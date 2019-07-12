package com.thinkgem.jeesite.API.entity;



public class SocketMsg {
    String flag;
    String accountId;
    String teachinId;
    String interviewId;
    String title;
    String companyName;
    String content;

    public SocketMsg(){

    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public SocketMsg(String flag){
        this.flag=flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTeachinId() {
        return teachinId;
    }

    public void setTeachinId(String teachinId) {
        this.teachinId = teachinId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SocketMsg{" +
                "flag='" + flag + '\'' +
                ", accountId='" + accountId + '\'' +
                ", teachinId='" + teachinId + '\'' +
                ", interviewId='" + interviewId + '\'' +
                ", title='" + title + '\'' +
                ", companyName='" + companyName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
