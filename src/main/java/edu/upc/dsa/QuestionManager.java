package edu.upc.dsa;
import edu.upc.dsa.models.Question;
import edu.upc.dsa.models.User;

import java.util.List;


public interface QuestionManager {

    public Question addQuestion(Question question);

    public int questionListSize();

    public List<Question> getAllQuestions();


}
