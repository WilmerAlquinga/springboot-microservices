package dev.wsalquinga.accounts_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wsalquinga on 27/10/2023
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "movement", schema = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long id;

    @CreatedDate
    @Column(name = "movement_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "movement_type", nullable = false, length = 16)
    private String movementType;

    @Column(name = "movement_amount", nullable = false, precision = 2)
    private BigDecimal amount;

    @Column(name = "movement_balance", nullable = false, precision = 2)
    private BigDecimal balance;

    @CreatedDate
    @Column(name = "movement_created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "movement_created_by", nullable = false, length = 128)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "movement_updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "movement_updated_by", nullable = false, length = 128)
    private String updatedBy;

    @Column(name = "movement_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_id",
            nullable = false
    )
    @ToString.Exclude
    private Account account;

    @PrePersist
    @PreUpdate
    public void establishMovementType() {
        String movementType = "Retiro";
        if (this.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
            movementType = "Depósito";
        }
        this.setMovementType(movementType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
