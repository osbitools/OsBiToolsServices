package com.osbitools.ws.rest.prj.shared.dto;

public class RevCommitDto {

  private String id;
  
  private String comment;
  
  private Integer commitTime;
  
  private String committer;

  public RevCommitDto(String id, String comment, 
              int commitTime, String committer) {
    this.id = id;
    this.comment = comment;
    this.commitTime = commitTime;
    this.committer = committer;
  }
  
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment the comment to set
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * @return the commitTime
   */
  public Integer getCommitTime() {
    return commitTime;
  }
  
  /**
   * @param commitTime the commitTime to set
   */
  public void setCommitTime(Integer commitTime) {
    this.commitTime = commitTime;
  }

  /**
   * @return the committer
   */
  public String getCommitter() {
    return committer;
  }

  /**
   * @param committer the committer to set
   */
  public void setCommitter(String committer) {
    this.committer = committer;
  }
}
