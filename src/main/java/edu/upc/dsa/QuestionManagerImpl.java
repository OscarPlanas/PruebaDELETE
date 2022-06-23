package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.lang.Object;
import java.util.*;


public class QuestionManagerImpl implements QuestionManager{
    //logs
    final static Logger logger = Logger.getLogger(ItemManagerImpl.class);
    private List<Question> questionList;
    private static QuestionManagerImpl instance;

    private QuestionManagerImpl() {
        this.questionList = new LinkedList<>();
    }
    //Singleton
    public static QuestionManagerImpl getInstance() {
        //logger.info(instance);
        if (instance == null)
            instance = new QuestionManagerImpl();
        //logger.info(instance);
        return instance;
    }

    //Publicamos una pregunta
    @Override
    public Question addQuestion(Question question) {

        String pregunta = question.getDate();
        for (Question i : this.questionList) {
            if (i.getDate().equals(pregunta)) {
                logger.info("Question " + pregunta + " encontrada");
                return null;
            }
        }
        logger.info("Nueva question: " + question);
        this.questionList.add(question);
        logger.info("Nueva question añadida: " + question);
        return question;
    }

    @Override
    public int questionListSize() {
        return this.questionList.size();
    }

    @Override
    public List<Question> getAllQuestions(){
        return this.questionList;
    }


}
