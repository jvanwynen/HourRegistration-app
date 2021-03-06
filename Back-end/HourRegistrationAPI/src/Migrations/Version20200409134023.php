<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20200409134023 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->abortIf($this->connection->getDatabasePlatform()->getName() !== 'mysql', 'Migration can only be executed safely on \'mysql\'.');

        $this->addSql('ALTER TABLE hour DROP FOREIGN KEY FK_701E114EA76ED395');
        $this->addSql('DROP INDEX IDX_701E114EA76ED395 ON hour');
        $this->addSql('ALTER TABLE hour DROP user_id, CHANGE hours hours INT DEFAULT NULL');
        $this->addSql('ALTER TABLE user CHANGE company_id company_id INT DEFAULT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->abortIf($this->connection->getDatabasePlatform()->getName() !== 'mysql', 'Migration can only be executed safely on \'mysql\'.');

        $this->addSql('ALTER TABLE hour ADD user_id BIGINT DEFAULT NULL, CHANGE hours hours INT DEFAULT NULL');
        $this->addSql('ALTER TABLE hour ADD CONSTRAINT FK_701E114EA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_701E114EA76ED395 ON hour (user_id)');
        $this->addSql('ALTER TABLE user CHANGE company_id company_id INT DEFAULT NULL');
    }
}
