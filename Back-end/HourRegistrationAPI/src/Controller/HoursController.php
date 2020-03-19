<?php

namespace App\Controller;

use App\Entity\Hour;
use App\Repository\HourRepository;
use App\Repository\ProjectRepository;
use App\Repository\UserRepository;
use DateTime;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HoursController extends AbstractController
{
/**TODO:
 * InsertHours()
 * GetHoursByUserId()
 * GetHoursByProjectId()
 * GetAllHours()
 * UpdateHours()
 * DeleteHours()
**/

    /**
     * @Route("/hours/insert", name="hours")
     * @param Request $request
     * @param HourRepository $hourRepository
     * @param UserRepository $userRepository
     * @param ProjectRepository $projectRepository
     * @return Response
     * @throws \Exception
     */
    function insertHours(Request $request, HourRepository $hourRepository, UserRepository $userRepository, ProjectRepository $projectRepository){
        $entityManager = $this->getDoctrine()->getManager();
        $response = new Response();

        // the URI being requested (e.g. /about) minus any query parameters
        $workedHours = $request->request->get('workedhours');
        $userId = $request->request->get('userid');
        $projectId = $request->request->get('projectid');
        $dateAndTime = $request->request->get('dateandtime');
        //Query the database to find the corrosponding company object based on the ID
        $user = $userRepository->find($userId);
        $project = $projectRepository->find($projectId);

        $hour = new Hour();
        $hour->setWorkedhours($workedHours);
        $hour->setUser($user);
        $hour->setProject($project);

        $date = new DateTime($dateAndTime);
        $hour->setDateAndTime( $date);

        if($hour != null){
            // tell Doctrine you want to (eventually) save the Product (no queries yet)
            $entityManager->persist($hour);
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
     * @Route("/hours/getbyid")
     * @param Request $request
     * @param HourRepository $hourRepository
     * @return JsonResponse
     */
    function getHoursById(Request $request, HourRepository $hourRepository){
        $response = new JsonResponse(['error' => "No Hours has been found with this ID"]);
        $userid = $request->query->get('id');
        $hour = $hourRepository->find($userid);
        if ($hour != null) {
            // the data to send when creating the response
            $response = new JsonResponse(['id' => $hour->getId(), 'workedhours' => $hour->getWorkedhours(), 'dateandtime' => $hour->getDateAndTime()->format('Y-m-d H:i'),
                "userid" => $hour->getUser()->getId(), "projectid" => $hour->getProject()->getId()]);
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
     * @Route("/hours/getbyuserid")
     * @param Request $request
     * @param UserRepository $userRepository
     * @return JsonResponse
     */
    function getHoursByUserId(Request $request, UserRepository $userRepository){
        $response = new JsonResponse(['error' => "No data has been found!"]);
        $userid = $request->query->get('userid');
        $user = $userRepository->find($userid);
        if($user != null){
            $hours = $user->getHours();
            $hoursArray = array();
            if(!$hours->isEmpty()){
                foreach ($hours as $item) {
                    $hoursArray[] = array(
                        'id' => $item->getId(),
                        'workedhours' => $item->getWorkedhours(),
                        'userid' => $item->getUser()->getId(),
                        'projectid' => $item->getProject()->getId(),
                        'dateandtime' => $item->getDateAndTime()->format('Y-m-d H:i')
                    );
                }
                $response = new JsonResponse($hoursArray);
                // the data to send when creating the response
                $response->setStatusCode(Response::HTTP_OK);
                // sets a HTTP response header
                $response->headers->set('Content-Type', 'application/json');
                return $response;
            }
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }

    /**
     * @Route("/hours/getbyprojectid")
     * @param Request $request
     * @param ProjectRepository $projectRepository
     * @return JsonResponse
     */
    function getHoursByProjectId(Request $request, ProjectRepository $projectRepository){
        $response = new JsonResponse(['error' => "No data has been found!"]);
        $projectid = $request->query->get('projectid');
        $project = $projectRepository->find($projectid);
        if($project != null){
            $hours = $project->getHours();
            $hoursArray = array();
            if(!$hours->isEmpty()){
                foreach ($hours as $item) {
                    $hoursArray[] = array(
                        'id' => $item->getId(),
                        'workedhours' => $item->getWorkedhours(),
                        'userid' => $item->getUser()->getId(),
                        'projectid' => $item->getProject()->getId(),
                        'dateandtime' => $item->getDateAndTime()->format('Y-m-d H:i')
                    );
                }
                $response = new JsonResponse($hoursArray);
                // the data to send when creating the response
                $response->setStatusCode(Response::HTTP_OK);
                // sets a HTTP response header
                $response->headers->set('Content-Type', 'application/json');
                return $response;
            }
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        // sets a HTTP response header
        $response->headers->set('Content-Type', 'text/html');
        return $response;
    }
    /**
     * @Route("/hours/allhours")
     * @param Request $request
     * @param HourRepository $hourRepository
     * @return JsonResponse
     */
    function getAllHours(Request $request, HourRepository $hourRepository){
        $response = new JsonResponse(['error' => "No hours has been found"]);
        $hours = $hourRepository->findAll();
        if($hours != null) {
            foreach ($hours as $item) {
                $hoursArray[] = array(
                    'id' => $item->getId(),
                    'workedhours' => $item->getWorkedhours(),
                    'userid' => $item->getUser()->getId(),
                    'firstname' => $item->getUser()->getFirstname(),
                    'projectid' => $item->getProject()->getId(),
                    'projectname' => $item->getProject()->getProjectname(),
                    'dateandtime' => $item->getDateAndTime()->format('Y-m-d H:i')
                );
            }
            $response = new JsonResponse($hoursArray);
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        $response->headers->set('Content-Type', 'application/json');
        return $response;
    }

    /**
     * @Route("/hours/deletebyid")
     * @param Request $request
     * @param HourRepository $hourRepository
     * @return Response
     */
    function deleteHourById(Request $request, HourRepository $hourRepository) {
        $entityManager = $this->getDoctrine()->getManager();
        $hourid = $request->request->get('id');
        $hourToDelete = $hourRepository->find($hourid);
        if($hourToDelete != null){
            $entityManager->remove($hourToDelete);
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
     * @Route("/hours/updatebyid")
     * @param Request $request
     * @param HourRepository $hourRepository
     * @return Response
     * @throws \Exception
     */
    function updateHourById(Request $request, HourRepository $hourRepository){
        $entityManager = $this->getDoctrine()->getManager();
        // the URI being requested (e.g. /about) minus any query parameters
        $id = $request->request->get('id');
        $workedhours = $request->request->get('workedhours');
        $dateAndTime = $request->request->get('dateandtime');
        $hour = $hourRepository->find($id);
        if($hour != null) {
            $hour->setWorkedhours($workedhours);
            $date = new DateTime($dateAndTime);
            $hour->setDateAndTime( $date);
            $entityManager->persist($hour);
            $entityManager->flush();
            $responseHour = $hourRepository->find($id);
            $response = new JsonResponse(['id' => $responseHour->getId(), 'workedhours' => $responseHour->getWorkedhours(), 'projectid' => $responseHour->getProject()->getId(),
                'projectname' => $responseHour->getProject()->getProjectname(), 'dateandtime' => $responseHour->getDateAndTime()->format('Y-m-d H:i')]);
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
}
