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

interface Header {
  matricula: string;
  ficha: string;
}


/**
 * Extract headers from a page using vertical gap grouping
 */
function extractHeadersFromPage(page: Page): Record<string, any> {
  const headers: Record<string, any>[] = [];
  const linesGrouped = page._groupLinesByVerticalGaps(0.2, 0.2).lines;
  let content = "";

  linesGrouped.forEach((lineGroup: any, index: number) => {
    let header = "";
    let registry = "";
    let ficha = "";

    lineGroup.forEach((line: any) => {
      if (index === 0) {
        if (line.text.startsWith("Matr")) {
          registry += line.text.replace("Matr", "").trim();
        } else if (line.text.startsWith("Ficha") || line.text.startsWith("0")) {
          ficha += line.text.replace("Ficha", "").trim();
        } else if (/\d+\.\d+/.test(line.text)) {
          registry += line.text;
        } else {
          header += line.text
        }
      }
      if (line.text.trim().length > 50) {
        content += line.text;
      }
      if (index > 0) {
        content += line.text
      }
    });

    if (index === 0 && registry || ficha) headers.push({ registry, ficha });


  });

  return { headers, content }
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
    console.log(headers);

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
