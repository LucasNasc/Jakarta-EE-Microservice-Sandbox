/**
 * Example script using TRP.js from NodeJS with ES-style module imports in TypeScript
 *
 * This script shows how you can get started with TRP using either local Amazon Textract JSON
 * response files, or calling synchronous Amazon Textract APIs.
 */
import { readFile } from "node:fs/promises";
import {
  ApiAnalyzeDocumentResponse,
  Page,
  TextractDocument,
} from "amazon-textract-response-parser";

/**
 * Load and parse a static JSON file into a TextractDocument
 */
async function loadTextractDocument(filePath: string): Promise<TextractDocument> {
  const staticTestResponse: ApiAnalyzeDocumentResponse = JSON.parse(
    await readFile(filePath, "utf-8"),
  );
  return new TextractDocument(staticTestResponse);
}

/**
 * Extract headers from a page using vertical gap grouping
 */
function extractHeadersFromPage(page: any): string[] {
  const headers: string[] = [];
  const linesGrouped = page._groupLinesByVerticalGaps(0.2, 0.2).lines;


  linesGrouped.forEach((lineGroup: any, index: number) => {
    let header = "";
    let registry = "";
    let ficha = "";

    lineGroup.forEach((line: any) => {

      if (line.text.startsWith("Matr")) {
        registry += line.text + "\n";
      } else if (line.text.startsWith("Ficha") || line.text.startsWith("0")) {
        ficha += line.text;
      } else if (line.text.includes(".")) {
        registry += line.text + "\n";
      } else {
        header += line.text + "\n"
      }
    });

    header = registry + `\n` + ficha;
    if (index === 0) headers.push(header);

  });

  return headers;
}

function extractContentFromPage(page: Page): string {

  const linesGrouped = page.getLinesByLayoutArea();
  let pageContent = "";
  linesGrouped.content.forEach((line: any, index: number) => {
    pageContent += line.text + "\n";


  });

  return pageContent;
}

/**
 * Process each page of the document to extract headers
 */
async function processDocument(filePath: string) {
  const textractDoc = await loadTextractDocument(filePath);
  const pages = textractDoc.listPages();
  console.log(pages.length)
  pages.forEach((page, pageIndex) => {
    const headers = extractHeadersFromPage(page);
    console.log(`Headers for Page ${pageIndex + 1}:`);
    console.log(headers.join("\n"));
    console.log("--------------------");
    const pageContent = extractContentFromPage(page);
    console.log(`Content for Page ${pageIndex + 1}:`);
    console.log(pageContent);
  });
}

/**
 * Main function to test static files
 */
async function testStaticFiles() {
  const filePath = "../../test/data/test-response-3.json";
  await processDocument(filePath);
}

testStaticFiles().then(() => console.log("Done!"));
