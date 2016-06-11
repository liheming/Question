package com.company.question.questionapplication.Bean;

/**
 * Created by ZCJ on 2016/6/11.
 */
public class ChoiceInfo {
    private int position;
    private int checked;
    private int answer;
    private boolean isTrue;

    @Override
    public String toString() {
        return "{ position:"+position+", checked:"+checked+", answer:"+answer+", isTrue:"+isTrue+" }";
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
