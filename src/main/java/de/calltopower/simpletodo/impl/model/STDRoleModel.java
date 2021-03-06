package de.calltopower.simpletodo.impl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.calltopower.simpletodo.api.model.STDModel;
import de.calltopower.simpletodo.impl.enums.STDUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model implementation for roles
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cache(region = "roles", usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, exclude = "users")
@Table(name = STDRoleModel.TABLE_NAME)
public class STDRoleModel implements Serializable, STDModel {

    private static final long serialVersionUID = -8575649400565741811L;

    /**
     * The table name
     */
    public static final String TABLE_NAME = "roles";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "NR_ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STR_NAME", length = 20)
    private STDUserRole name;

    @Builder.Default
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<STDUserModel> users = new ArrayList<>();

    @Override
    public String toString() {
        // @formatter:off
        return String.format(
                "STDUserRoleModel["
                    + "id='%s',"
                    + "name='%s'"
                + "]",
                id,
                name
               );
        // @formatter:on
    }

}
