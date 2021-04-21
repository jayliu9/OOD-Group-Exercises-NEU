package problem;

import java.util.Date;
import java.util.Objects;

public class ToDo implements Comparable<ToDo>{

  private int ID;
  private String text; // The only required parameter
  private boolean completed; // Default false
  private Date due;
  private int priority; // Default 3
  private String category;

  private ToDo(Builder builder) {
    this.ID = builder.ID;
    this.text = builder.text;
    this.completed = builder.completed;
    this.due = builder.due;
    this.priority = builder.priority;
    this.category = builder.category;
  }
  public int getID() {
    return this.ID;
  }

  public String getText() {
    return this.text;
  }

  public boolean getCompleted() {
    return this.completed;
  }

  public boolean isCompleted() {
    return this.completed == true;
  }

  public Date getDue() {
    return this.due;
  }

  public int getPriority() {
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
    private boolean completed; // Default false
    private Date due;
    private int priority; // Default is 4
    private String category;
    private int ID;
    private static final int LOWEST_PRIORITY = 4;

    public Builder(String text) {
      this.text = text;
      this.completed = false;
      this.priority = LOWEST_PRIORITY;
    }

    public Builder setID(int ID) {
      this.ID = ID;
      return this;
    }

    public Builder setCompleted(boolean completed) {
      this.completed = completed;
      return this;
    }

    public Builder addDue(Date due) {
      this.due = due;
      return this;
    }

    public Builder addPriority(int priority) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ToDo toDo = (ToDo) o;
    return this.ID == toDo.ID && this.completed == toDo.completed && this.priority == toDo.priority &&
            this.text.equals(toDo.text) && Objects.equals(this.due, toDo.due) &&
            Objects.equals(this.category, toDo.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.ID, this.text, this.completed, this.due, this.priority, this.category);
  }
}
