<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass="App\Repository\ProjectRepository")
 */
class Project
{
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50)
     */
    private $projectname;

    /**
     * @ORM\Column(type="string", length=50)
     */
    private $project_tag;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Company", inversedBy="projects")
     * @ORM\JoinColumn(nullable=false)
     */
    private $company;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Hour", mappedBy="project", orphanRemoval=true)
     */
    private $hours;

    public function __construct()
    {
        $this->hours = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getProjectname(): ?string
    {
        return $this->projectname;
    }

    public function setProjectname(string $projectname): self
    {
        $this->projectname = $projectname;

        return $this;
    }

    public function getProjectTag(): ?string
    {
        return $this->project_tag;
    }

    public function setProjectTag(string $project_tag): self
    {
        $this->project_tag = $project_tag;

        return $this;
    }

    public function getCompany(): ?Company
    {
        return $this->company;
    }

    public function setCompany(?Company $company): self
    {
        $this->company = $company;

        return $this;
    }

    /**
     * @return Collection|Hour[]
     */
    public function getHours(): Collection
    {
        return $this->hours;
    }

    public function addHour(Hour $hour): self
    {
        if (!$this->hours->contains($hour)) {
            $this->hours[] = $hour;
            $hour->setProject($this);
        }
        return $this;
    }

    public function removeHour(Hour $hour): self
    {
        if ($this->hours->contains($hour)) {
            $this->hours->removeElement($hour);
            // set the owning side to null (unless already changed)
            if ($hour->getProject() === $this) {
                $hour->setProject(null);
            }
        }
        return $this;
    }
}
