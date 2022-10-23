package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1__init_table extends BaseJavaMigration {


    @Override
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("drop table if exists tasks;\n" +
                        "create table tasks(\n" +
                        "    id int primary key auto_increment,\n" +
                        "    description varchar(100) not null,\n" +
                        "    done bit\n" +
                        " )");
    }
}
