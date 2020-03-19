<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20200312192634 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->abortIf($this->connection->getDatabasePlatform()->getName() !== 'mysql', 'Migration can only be executed safely on \'mysql\'.');

        $this->addSql('ALTER TABLE hour CHANGE workedhours workedhours INT DEFAULT NULL');
        $this->addSql('ALTER TABLE project DROP FOREIGN KEY FK_2FB3D0EE38B53C32');
        $this->addSql('DROP INDEX IDX_2FB3D0EE38B53C32 ON project');
        $this->addSql('ALTER TABLE project CHANGE company_id_id company_id INT NOT NULL');
        $this->addSql('ALTER TABLE project ADD CONSTRAINT FK_2FB3D0EE979B1AD6 FOREIGN KEY (company_id) REFERENCES company (id)');
        $this->addSql('CREATE INDEX IDX_2FB3D0EE979B1AD6 ON project (company_id)');
        $this->addSql('ALTER TABLE user CHANGE admin admin TINYINT(1) DEFAULT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->abortIf($this->connection->getDatabasePlatform()->getName() !== 'mysql', 'Migration can only be executed safely on \'mysql\'.');

        $this->addSql('ALTER TABLE hour CHANGE workedhours workedhours INT DEFAULT NULL');
        $this->addSql('ALTER TABLE project DROP FOREIGN KEY FK_2FB3D0EE979B1AD6');
        $this->addSql('DROP INDEX IDX_2FB3D0EE979B1AD6 ON project');
        $this->addSql('ALTER TABLE project CHANGE company_id company_id_id INT NOT NULL');
        $this->addSql('ALTER TABLE project ADD CONSTRAINT FK_2FB3D0EE38B53C32 FOREIGN KEY (company_id_id) REFERENCES company (id)');
        $this->addSql('CREATE INDEX IDX_2FB3D0EE38B53C32 ON project (company_id_id)');
        $this->addSql('ALTER TABLE user CHANGE admin admin TINYINT(1) DEFAULT \'NULL\'');
    }
}
