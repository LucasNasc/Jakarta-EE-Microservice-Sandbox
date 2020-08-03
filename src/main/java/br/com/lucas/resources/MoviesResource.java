package br.com.lucas.resources;
import br.com.lucas.dto.MovieDTO;
import br.com.lucas.exceptions.BusinessException;
import br.com.lucas.services.MovieServices;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("movies")
@RequestScoped
public class MoviesResource {

    @Inject
    private MovieServices service;

    @Context
    UriInfo uriInfo;

    @GET
    @Operation(summary = "Get All Movies",
            description = "This method list all movies from repository")
    @APIResponse(responseCode = "200",
            description = "Everything went ok, we found this movies: ",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieDTO.class)))
    @APIResponse(responseCode = "404",
            description = "Todo not found")
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {

        return Response.status(Response.Status.OK).entity(service.listAll())
                .links(getHateoas("listALL", "GET")).build();

    }
    @GET
    @Path("{id}")
    @Operation(summary = "Get Movie By Code",
            description = "This method get movie from repository by id")
    @APIResponse(responseCode = "200",
            description = "Everything went ok, we get a movie",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieDTO.class)))
    @APIResponse(responseCode = "404",
            description = "Movie not found")
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {

        try {
            MovieDTO dto = service.findById(id);
            return Response.status(Response.Status.OK).entity(dto)
                    .links(getHateoas("findById", "GET")).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage())
                    .links(getHateoas("findById", "GET")).build();
        }

    }

    @POST
    @Operation(summary = "Save Movie",
            description = "This method Save movie from body of request")
    @APIResponse(responseCode = "200", description = "Everything went ok, we save your movie ")
    @APIResponse(responseCode = "409", description = "movie Already exist")
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMovie(@Valid MovieDTO dto) {

        if (service.duoVerify(dto)) {
            return Response.status(Response.Status.CONFLICT).entity("Movie_Already_Exist")
                    .links(getHateoas("save", "POST")).build();
        } else {
            service.save(dto);
            return Response.status(Response.Status.CREATED)
                    .links(getHateoas("save", "POST")).build();
        }
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Update Movie",
                 description = "This method update movie from data of body request")
    @APIResponse(responseCode = "200", description = "Everything went ok, we update your movie ")
    @APIResponse(responseCode = "404", description = "not found movie on repository")
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, @Valid MovieDTO dto) {

        try {
            return Response.status(Response.Status.OK).entity(service.update(dto))
                    .links(getHateoas("update", "PUT")).build();
        }catch (BusinessException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .links(getHateoas("update", "PUT")).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Save Movie",
            description = "This method delete movie by id")
    @APIResponse(responseCode = "204", description = "Everything went ok, we delete your movie ")
    @APIResponse(responseCode = "404", description = "We not found your movie in repository")
    @Metered
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id) {

        try{
            service.delete(id);
            return Response.status(Response.Status.NO_CONTENT)
                    .links(getHateoas("deleteMovies", "DELETE")).build();
        } catch (BusinessException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage())
                    .links(getHateoas("deleteMovies", "DELETE")).build();
        }
    }

    public Link getHateoas(String methodName, String httpVerb) {
        return Link.fromUri(uriInfo.getAbsolutePath())
                .rel(methodName)
                .type(httpVerb).build();
    }


}
