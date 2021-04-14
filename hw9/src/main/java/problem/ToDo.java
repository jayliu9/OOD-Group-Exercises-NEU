package problem;

import java.util.Date;

public class ToDo implements Comparable<ToDo>{

  private String ID;
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
  public String getID() {
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
    private int priority; // Default 3
    private String category;
    private String ID;
    private static final int LOWEST_PRIORITY = 3;

    public Builder(String text) {
      this.text = text;
      this.completed = false;
      this.priority = LOWEST_PRIORITY;
    }

    public Builder setID(String ID) {
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
}
