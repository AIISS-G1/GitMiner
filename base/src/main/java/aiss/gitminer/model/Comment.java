package aiss.gitminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table
public class Comment {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("body")
    @NotEmpty(message = "The message cannot be empty.")
    @Column(columnDefinition = "TEXT")
    private String body;

    @JsonProperty("author")
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private User author;

    @JsonProperty("created_at")
    @NotNull(message = "The field created_at cannot be null.")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    public Comment() {
        // Constructor for Jackson
    }

    public Comment(String id, String body, User author, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(id, comment.id) && Objects.equals(body, comment.body) && Objects.equals(author, comment.author) && Objects.equals(createdAt, comment.createdAt) && Objects.equals(updatedAt, comment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, author, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Comment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("body");
        sb.append('=');
        sb.append(((this.body == null) ? "<null>" : this.body));
        sb.append(',');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null) ? "<null>" : this.author));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null) ? "<null>" : this.createdAt));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null) ? "<null>" : this.updatedAt));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
