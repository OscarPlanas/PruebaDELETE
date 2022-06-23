package edu.upc.dsa.services;

import edu.upc.dsa.QuestionManager;
import edu.upc.dsa.QuestionManagerImpl;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Api(value = "/question", description = "Endpoint to User Service")
@Path("/question")

public class QuestionServices {

    private QuestionManager manager;

    public QuestionServices() {
        this.manager = QuestionManagerImpl.getInstance();
        if (manager.questionListSize() == 0) {
            Question u1 = this.manager.addQuestion(new Question("3/06/2022", "Exmane2", "Prueba para realizar el examen2", "Oscar"));

        }
    }

    //Examen minimo2
    //question
    @POST
    @ApiOperation(value = "question", notes = "date,title,message,sender")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/question")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addQuestion(Question q) {

        Question question = new Question(q.getDate(), q.getTitle(), q.getMessage(), q.getSender());
        if (question.getDate().equals("") || question.getTitle().equals("") || question.getMessage().equals("") || question.getSender().equals("")) {
            return Response.status(500).build();
        }
        for (Question qs : this.manager.getAllQuestions()) {
            if (qs.getDate().equals(question.getDate())) {
                return Response.status(500).build();
            }
        }
        question.setTitle(q.getTitle());
        question.setMessage(q.getMessage());
        question.setSender(q.getSender());
        this.manager.addQuestion(question);
        return Response.status(200).entity(question).build();
    }

    //Get de la lista de preguntas
    @GET
    @ApiOperation(value = "Get de todas las preguntas", notes = "Get todas las preguntas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllQuestions() {
        List<Question> questions = this.manager.getAllQuestions();
        GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(questions){};
        return Response.status(201).entity(entity).build();
    }



}
