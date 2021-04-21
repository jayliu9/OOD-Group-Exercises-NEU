package problem;

import java.util.Date;
import java.util.Objects;

public class ToDo implements Comparable<ToDo>{

  private Integer ID;
  private String text; // The only required parameter
  private Boolean completed; // Default false
  private Date due;
  private Integer priority; // Default 3
  private String category;

  private ToDo(Builder builder) {
    this.ID = builder.ID;
    this.text = builder.text;
    this.completed = builder.completed;
    this.due = builder.due;
    this.priority = builder.priority;
    this.category = builder.category;
  }
  public Integer getID() {
    return this.ID;
  }

  public String getText() {
    return this.text;
  }

  public Boolean getCompleted() {
    return this.completed;
  }

  public Date getDue() {
    return this.due;
  }

  public Integer getPriority() {
    return this.priority;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Override
  public int compareTo(ToDo o) {
    return 0;
  }

  public static class Builder {

    private String text; // The only required parameter
    private Boolean completed; // Default false
    private Date due;
    private Integer priority; // Default is 4
    private String category;
    private Integer ID;

    public Builder(String text) {
      this.text = text;
      this.completed = false;
    }

    public Builder setID(Integer ID) {
      this.ID = ID;
      return this;
    }

    public Builder setCompleted(Boolean completed) {
      this.completed = completed;
      return this;
    }

    public Builder addDue(Date due) {
      this.due = due;
      return this;
    }

    public Builder addPriority(Integer priority) {
      this.priority = priority;
      return this;
    }

    public Builder addCategory(String category) {
      this.category = category;
      return this;
    }

    public ToDo build() {
      return new ToDo(this);
    }

  }

  @Override
  public String toString() {
    return "ToDo{" +
        "ID='" + ID + '\'' +
        ", text='" + text + '\'' +
        ", completed=" + completed +
        ", due=" + due +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToDo toDo = (ToDo) o;
    return ID.equals(toDo.ID) && text.equals(toDo.text) && completed.equals(toDo.completed)
        && Objects.equals(due, toDo.due) && Objects
        .equals(priority, toDo.priority) && Objects.equals(category, toDo.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, text, completed, due, priority, category);
  }
}
