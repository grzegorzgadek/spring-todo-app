package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V9__add_project_steps_table extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("create table project_steps(\n" +
                        "    id int primary key auto_increment,\n" +
                        "    description varchar(100) not null," +
                        "    project_id int not null," +
                        "    days_to_deadline int not null," +
                        "    foreign key (project_id) references projects (id));");
    }
}
