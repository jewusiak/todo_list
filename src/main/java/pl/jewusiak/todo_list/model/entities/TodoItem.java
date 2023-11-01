package pl.jewusiak.todo_list.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    private String description;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private TodoList parentList;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime modifiedAt;
}
