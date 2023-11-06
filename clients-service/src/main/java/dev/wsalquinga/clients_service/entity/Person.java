package dev.wsalquinga.clients_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wsalquinga on 26/10/2023
 */
@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "person_name", nullable = false, length = 128)
    private String name;

    @Column(name = "person_gender", nullable = false, length = 32)
    private String gender;

    @Column(name = "person_age")
    private Integer age;

    @Column(name = "person_document_number", nullable = false, length = 16)
    private String documentNumber;

    @Column(name = "person_address", length = 128)
    private String address;

    @Column(name = "person_phone_number", length = 32)
    private String phoneNumber;

    @CreatedDate
    @Column(name = "person_created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "person_created_by", nullable = false, length = 128)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "person_updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "person_updated_by", nullable = false, length = 128)
    private String updatedBy;

    @Column(name = "person_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
