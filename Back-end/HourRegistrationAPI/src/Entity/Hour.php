<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass="App\Repository\HourRepository")
 */
class Hour
{
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer", nullable=true)
     */
    private $workedhours;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User", inversedBy="hours")
     * @ORM\JoinColumn(nullable=false)
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Project", inversedBy="hours")
     * @ORM\JoinColumn(nullable=false)
     */
    private $project;

    /**
     * @ORM\Column(type="datetime")
     */
    private $DateAndTime;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getWorkedhours(): ?int
    {
        return $this->workedhours;
    }

    public function setWorkedhours(?int $workedhours): self
    {
        $this->workedhours = $workedhours;

        return $this;
    }

    public function getUser(): ?USer
    {
        return $this->user;
    }

    public function setUser(?USer $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getProject(): ?Project
    {
        return $this->project;
    }

    public function setProject(?Project $project): self
    {
        $this->project = $project;

        return $this;
    }

    public function getDateAndTime(): ?\DateTimeInterface
    {
        return $this->DateAndTime;
    }

    public function setDateAndTime(\DateTimeInterface $DateAndTime): self
    {
        $this->DateAndTime = $DateAndTime;

        return $this;
    }
}
