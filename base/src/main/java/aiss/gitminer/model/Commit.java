package aiss.gitminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table
public class Commit {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    @Column(columnDefinition="TEXT")
    private String title;

    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;

    @JsonProperty("author_name")
    @NotEmpty(message = "Author name cannot be empty.")
    private String authorName;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("authored_date")
    @NotNull(message = "Author date cannot be empty.")
    private Instant authoredDate;

    @JsonProperty("committer_name")
    @NotEmpty(message = "Committer name cannot be empty.")
    private String committerName;

    @JsonProperty("committer_email")
    private String committerEmail;

    @JsonProperty("committed_date")
    @NotNull(message = "Committer date cannot be empty.")
    private Instant committedDate;

    @JsonProperty("web_url")
    @NotEmpty(message = "URL cannot be empty.")
    private String webUrl;

    public Commit() {
        // Constructor for Jackson
    }

    public Commit(String id, String title, String message, String authorName, String authorEmail, Instant authoredDate,
                  String committerName, String committerEmail, Instant committedDate, String webUrl) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authoredDate = authoredDate;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
        this.committedDate = committedDate;
        this.webUrl = webUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Instant getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(Instant authoredDate) {
        this.authoredDate = authoredDate;
    }

    public String getCommitterName() {
        return committerName;
    }

    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    public Instant getCommittedDate() {
        return committedDate;
    }

    public void setCommittedDate(Instant committedDate) {
        this.committedDate = committedDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commit commit)) return false;
        return Objects.equals(id, commit.id) && Objects.equals(title, commit.title) && Objects.equals(message, commit.message) && Objects.equals(authorName, commit.authorName) && Objects.equals(authorEmail, commit.authorEmail) && Objects.equals(authoredDate, commit.authoredDate) && Objects.equals(committerName, commit.committerName) && Objects.equals(committerEmail, commit.committerEmail) && Objects.equals(committedDate, commit.committedDate) && Objects.equals(webUrl, commit.webUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message, authorName, authorEmail, authoredDate, committerName, committerEmail, committedDate, webUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Commit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null) ? "<null>" : this.title));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null) ? "<null>" : this.message));
        sb.append(',');
        sb.append("authorName");
        sb.append('=');
        sb.append(((this.authorName == null) ? "<null>" : this.authorName));
        sb.append(',');
        sb.append("authorEmail");
        sb.append('=');
        sb.append(((this.authorEmail == null) ? "<null>" : this.authorEmail));
        sb.append(',');
        sb.append("authoredDate");
        sb.append('=');
        sb.append(((this.authoredDate == null) ? "<null>" : this.authoredDate));
        sb.append(',');
        sb.append("committerName");
        sb.append('=');
        sb.append(((this.committerName == null) ? "<null>" : this.committerName));
        sb.append(',');
        sb.append("committerEmail");
        sb.append('=');
        sb.append(((this.committerEmail == null) ? "<null>" : this.committerEmail));
        sb.append(',');
        sb.append("committedDate");
        sb.append('=');
        sb.append(((this.committedDate == null) ? "<null>" : this.committedDate));
        sb.append(',');
        sb.append("webUrl");
        sb.append('=');
        sb.append(((this.webUrl == null) ? "<null>" : this.webUrl));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
