package problem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a To\Do class with a number of default/optional parameters. Uses the builder pattern
 * to handle construction.
 */
public class ToDo {

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

  /**
   * Gets the ID of the TO\Do.
   * @return the ID of the TO\Do.
   */
  public Integer getID() {
    return this.ID;
  }

  /**
   * Gets the description of the TO\Do.
   * @return the description of the TO\Do.
   */
  public String getText() {
    return this.text;
  }

  /**
   * Checks if the TO\Do is completed.
   * @return true if the TO\Do is completed; false otherwise.
   */
  public Boolean getCompleted() {
    return this.completed;
  }

  /**
   * Gets the due of the TO\Do.
   * @return the due of the TO\Do.
   */
  public Date getDue() {
    return this.due;
  }

  /**
   * Gets the priority of the TO\Do.
   * @return the priority of the TO\Do.
   */
  public Integer getPriority() {
    return this.priority;
  }

  /**
   * Gets the category of the TO\Do.
   * @return the category of the TO\Do.
   */
  public String getCategory() {
    return this.category;
  }

  /**
   * Sets the TO\Do to be completed or uncompleted.
   * @param completed A Boolean indicates whether the task is completed or incomplete。
   */
  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  /**
   * Uses the builder pattern to handle construction.
   */
  public static class Builder {

    private String text;
    private Boolean completed;
    private Date due;
    private Integer priority;
    private String category;
    private Integer ID;

    /**
     * Uses the builder pattern to handle construction.
     * @param text A description of the task to be done.
     */
    public Builder(String text) {
      this.text = text;
      this.completed = false;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @param ID A ID of the task to be done.
     * @return this object.
     */
    public Builder setID(Integer ID) {
      this.ID = ID;
      return this;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @param completed Indicates whether the task is completed or incomplete.
     * @return this object.
     */
    public Builder setCompleted(Boolean completed) {
      this.completed = completed;
      return this;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @param due A due date.
     * @return this object.
     */
    public Builder addDue(Date due) {
      this.due = due;
      return this;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @param priority An integer indicating the priority of the to\do.
     * @return this object.
     */
    public Builder addPriority(Integer priority) {
      this.priority = priority;
      return this;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @param category A user-specified String that can be used to group related todos.
     * @return this object.
     */
    public Builder addCategory(String category) {
      this.category = category;
      return this;
    }

    /**
     * Uses the builder pattern to handle construction.
     * @return this object.
     */
    public ToDo build() {
      return new ToDo(this);
    }

  }

  /**
   * Checks if two objects are equal
   *
   * @param o the object to compare this to
   * @return true if these two objects are equal, false otherwise.
   */
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

  /**
   * Gets a hash code value for the object.
   *
   * @return a hash code value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(ID, text, completed, due, priority, category);
  }

  /**
   * Creates a string representation of the Option.
   *
   * @return a string representation of the Option.
   */
  @Override
  public String toString() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String dueStr = null;
    if (due != null) {
      dueStr = formatter.format(due);
    }
    return "ToDo{" +
        "ID='" + ID + '\'' +
        ", text='" + text + '\'' +
        ", completed=" + completed +
        ", due=" + dueStr +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        '}';
  }
}