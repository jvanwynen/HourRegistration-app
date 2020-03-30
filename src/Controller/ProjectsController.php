<?php

namespace App\Controller;

use App\Entity\Project;
use App\Repository\CompanyRepository;
use App\Repository\ProjectRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProjectsController extends AbstractController
{
    /**
     * @Route("/projects", name="projects")
     */
    public function index()
    {
        return $this->json([
            'message' => 'Welcome to your new controller!',
            'path' => 'src/Controller/ProjectsController.php',
        ]);
    }

    /**
     * @Route("/project/insert", name="project")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return Response
     */
    function insertProject(Request $request, CompanyRepository $companyRepository){
        $entityManager = $this->getDoctrine()->getManager();
        $response = new Response();

        // the URI being requested (e.g. /about) minus any query parameters
        $projectname = $request->request->get('projectname');
        $project_tag = $request->request->get('project_tag');
        $companyid = $request->request->get('company_id');
        //Query the database to find the corrosponding company object based on the ID
        $company = $companyRepository->find($companyid);

        $project = new Project();
        $project->setName($projectname);
        $project->setTag($project_tag);
        $project->setCompany($company);

        if($project != null){
            // tell Doctrine you want to (eventually) save the Product (no queries yet)
            $entityManager->persist($project);
            // actually executes the queries (i.e. the INSERT query)
            $entityManager->flush();// tell Doctrine you want to (eventually) save the Product (no queries yet)
            $response->setStatusCode(Response::HTTP_OK);
            // sets a HTTP response header
            $response->headers->set('Content-Type', 'text/html');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NO_CONTENT);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }

    /**
     * @Route("/project/getbyid")
     * @param Request $request
     * @param ProjectRepository $projectRepository
     * @return JsonResponse
     */
    function getProjectById(Request $request, ProjectRepository $projectRepository){
        $response = new JsonResponse(['error' => "No project has been found with this ID"]);
        $projectid = $request->query->get('id');
        $project = $projectRepository->find($projectid);
        if ($project != null) {
            // the data to send when creating the response
            $response = new JsonResponse(['id' => $project->getId(), 'projectname' => $project->getName(), 'projecttag' => $project->getTag(),
                'companyid' => $project->getCompany()->getId()]);
            $response->setStatusCode(Response::HTTP_OK);
            // sets a HTTP response header
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }

    /**
     * @Route("/project/allprojects")
     * @param Request $request
     * @param ProjectRepository $projectRepository
     * @return JsonResponse
     */
    function getAllProjects(ProjectRepository $projectRepository)
    {
        $response = new JsonResponse(['error' => "No projects has been found"]);
        $projects = $projectRepository->findAll();
        if ($projects != null) {
            $usersArray = array();
            foreach ($projects as $item) {
                $usersArray[] = array(
                    'id' => $item->getId(),
                    'projectname' => $item->getName(),
                    'project_tag' => $item->getTag(),
                    'company_id' => $item->getCompany()->getId()
                );
            }
            $response = new JsonResponse($usersArray);
            $response->setStatusCode(Response::HTTP_OK);
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        $response->headers->set('Content-Type', 'application/json');
        return $response;
    }

    /**
     * @Route("/project/deletebyid")
     * @param Request $request
     * @param ProjectRepository $projectRepository
     * @return Response
     */
    function deleteProjectById(Request $request, ProjectRepository $projectRepository)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $projectid = $request->request->get('id');
        $projectoDelete = $projectRepository->find($projectid);
        if($projectoDelete != null){
            $entityManager->remove($projectoDelete);
            $entityManager->flush();

            $response = new Response();
            $response->setStatusCode(Response::HTTP_OK);
            // sets a HTTP response header
            $response->headers->set('Content-Type', 'text/html');

            return $response;
        }
        $response = new Response();
        $response->setStatusCode(Response::HTTP_NOT_FOUND);

        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');

        return $response;
    }

    /**
     * @Route("/project/updatebyid")
     * @param Request $request
     * @param ProjectRepository $projectRepository
     * @return Response
     */
    function updateProjectById(Request $request, ProjectRepository $projectRepository){
        $entityManager = $this->getDoctrine()->getManager();
        // the URI being requested (e.g. /about) minus any query parameters
        $id = $request->request->get('id');
        $project_name = $request->request->get('projectname');
        $project_tag = $request->request->get('project_tag');
        $project = $projectRepository->find($id);
        if($project != null) {
            $project->setName($project_name);
            $project->setTag($project_tag);
            $entityManager->persist($project);
            $entityManager->flush();
            $responseProject = $projectRepository->find($id);
            $response = new JsonResponse(['id' => $responseProject->getId(), 'projectname' => $responseProject->getName(), 'project_tag' => $responseProject->getTag()]);
            $response->setStatusCode(Response::HTTP_OK);
            // sets a HTTP response header
            $response->headers->set('Content-Type', 'text/html');
            return $response;
        }
        $response = new Response();
        $response->setStatusCode(Response::HTTP_NO_CONTENT);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }

    /**
     * @Route("/project/getbycompanyid")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return JsonResponse
     */
    function getProjectsByCompanyId(Request $request, CompanyRepository $companyRepository){
        $response = new JsonResponse(['error' => "No project has been found with this ID"]);
        $companyid = $request->query->get('companyid');
        $company = $companyRepository->find($companyid);
        if($company != null){
            $projects = $company->getProjects();
            $projectsArray = array();
            foreach ($projects as $item) {
                $projectsArray[] = array(
                    'id' => $item->getId(),
                    'projectname' => $item->getProjectname(),
                    'project_tag' => $item->getProjectTag(),
                    'company_id' => $item->getCompany()->getId()
                );
            }
            $response = new JsonResponse($projectsArray);
            // the data to send when creating the response
            $response->setStatusCode(Response::HTTP_OK);
            // sets a HTTP response header
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }
}
