package toyspringboot.server.Domain.Entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.client.HttpClientErrorException;
import toyspringboot.server.Domain.Dto.QnADto;
import toyspringboot.server.Domain.Dto.UserDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @ColumnDefault("no content")
    private String content;

    @Column
    @NotNull
    private Boolean isDeleted;

    @Column
    @NotNull
    private Timestamp createdDate;

    @Column
    @NotNull
    private Timestamp updatedDate;

    @Column
    private Timestamp deletedDate;

    @Column
    @NotNull
    private String creator;

    @Column
    @NotNull
    private String updater;

    @ManyToOne
    @JoinColumn(name = "User_email")
    private User user;
}
