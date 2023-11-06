package dev.wsalquinga.accounts_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
@Table(name = "account", schema = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "account_number", nullable = false, length = 16, unique = true)
    private String accountNumber;

    @Column(name = "account_type", nullable = false, length = 16)
    private String accountType;

    @Column(name = "account_balance", precision = 2)
    @DecimalMin(value = "0.00", message = "El Balance de la cuenta no puede ser menor a cero")
    private BigDecimal balance;

    @Column(name = "account_status")
    private Boolean status;

    @CreatedDate
    @Column(name = "account_created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "account_created_by", nullable = false, length = 128)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "account_updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "account_updated_by", nullable = false, length = 128)
    private String updatedBy;

    @Column(name = "account_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
