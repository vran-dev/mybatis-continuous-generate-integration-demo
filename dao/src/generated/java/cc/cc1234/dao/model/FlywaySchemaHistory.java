package cc.cc1234.dao.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table flyway_schema_history
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlywaySchemaHistory {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.installed_rank
     *
     * @mbg.generated
     */
    private Integer installedRank;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.version
     *
     * @mbg.generated
     */
    @Nullable
    private String version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.description
     *
     * @mbg.generated
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.type
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.script
     *
     * @mbg.generated
     */
    private String script;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.checksum
     *
     * @mbg.generated
     */
    @Nullable
    private Integer checksum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.installed_by
     *
     * @mbg.generated
     */
    private String installedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.installed_on
     *
     * @mbg.generated
     */
    private LocalDateTime installedOn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.execution_time
     *
     * @mbg.generated
     */
    private Integer executionTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flyway_schema_history.success
     *
     * @mbg.generated
     */
    private Boolean success;

    public Optional<String> getVersionOptional() {
        return Optional.ofNullable(version);
    }

    public Optional<Integer> getChecksumOptional() {
        return Optional.ofNullable(checksum);
    }
}