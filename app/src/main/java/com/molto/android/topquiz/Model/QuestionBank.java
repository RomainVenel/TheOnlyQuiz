package com.molto.android.topquiz.Model;

import java.util.Collections;
import java.util.List;

/**
 * Created by Romain on 03/06/2018.
 */

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        // Shuffle the question list before storing it
        Collections.shuffle(questionList);

        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        return mQuestionList.get(mNextQuestionIndex++);
    }
}
