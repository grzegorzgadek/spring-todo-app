package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V6__init_task_groups extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("create table task_groups(\n" +
                        "    id int primary key auto_increment,\n" +
                        "    description varchar(100) not null,\n" +
                        "    done bit\n" +
                        " );" +
                        "alter table tasks add column task_group_id int null;" +
                        "alter table tasks add foreign key (task_group_id) references task_groups (id)");
    }
}
