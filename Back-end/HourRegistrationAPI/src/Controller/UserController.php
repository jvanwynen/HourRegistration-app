<?php

namespace App\Controller;

use App\Entity\User;
use App\Repository\CompanyRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class UserController extends AbstractController
{
    /**
     * @Route("/user/insert", name="insertuser")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return Response
     */
    function insertUser(Request $request, CompanyRepository $companyRepository)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $response = new Response();
        // the URI being requested (e.g. /about) minus any query parameters
        $admin = $request->request->get('admin');
        $firstname = $request->request->get('firstname');
        $lastname = $request->request->get('lastname');
        $companyid = $request->request->get('companyid');
        //Query the database to find the corrosponding company object based on the ID
        $company = $companyRepository->find($companyid);

        $user = new User();
        $user->setAdmin($admin);
        $user->setFirstname($firstname);
        $user->setLastname($lastname);
        //set the users company by setting the right company
        $user->setCompany($company);

        if($user != null){
            // tell Doctrine you want to (eventually) save the Product (no queries yet)
            $entityManager->persist($user);
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
     * @Route("/user/getbyid", name="getuserbyid")
     * @param Request $request
     * @param UserRepository $userRepository
     * @return Response
     */
    function getUserById(Request $request, UserRepository $userRepository){
        $response = new JsonResponse(['error' => "No user has been found with this ID"]);
        $userid = $request->query->get('id');
        $user = $userRepository->find($userid);
        if ($user != null) {
            // the data to send when creating the response
            $response = new JsonResponse(['id' => $user->getId(), 'firstname' => $user->getFirstname(), 'lastname' => $user->getLastname(),
                'companyid' => $user->getCompany()->getId(), 'admin' => $user->getAdmin()]);
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
     * @Route("/user/allusers");
     * @param UserRepository $userRepository
     * @return Response
     */
    function getAllUsers(UserRepository $userRepository)
    {
        $response = new JsonResponse(['error' => "No users has been found"]);
        $users = $userRepository->findAll();
        if($users != null) {
            $usersArray = array();
            foreach ($users as $item) {
                $usersArray[] = array(
                    'id' => $item->getId(),
                    'firstname' => $item->getFirstname(),
                    'lastname' => $item->getLastname(),
                    'company' => $item->getCompany()->getCompanyname(),
                    'admin' => $item->getAdmin()
                );
            }
            $response = new JsonResponse($usersArray);
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        $response->headers->set('Content-Type', 'application/json');
        return $response;
    }

    /**
     * @Route("/user/deletebyid")
     * @param Request $request
     * @param UserRepository $userRepository
     * @return Response
     */
    function deleteUserById(Request $request, UserRepository $userRepository)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $userid = $request->request->get('id');
        $userToDelete = $userRepository->find($userid);
        if($userToDelete != null){
            $entityManager->remove($userToDelete);
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
     * @Route("/user/updatebyid")
     * @param Request $request
     * @param UserRepository $userRepository
     * @return Response
     */
    function updateUserById(Request $request, UserRepository $userRepository){
        $entityManager = $this->getDoctrine()->getManager();
        // the URI being requested (e.g. /about) minus any query parameters
        $id = $request->request->get('id');
        $admin = $request->request->get('admin');
        $firstname = $request->request->get('firstname');
        $lastname = $request->request->get('lastname');
        $user = $userRepository->find($id);
        if($user != null) {
            $user->setFirstname($firstname);
            $user->setLastname($lastname);
            $user->setAdmin($admin);
            $entityManager->persist($user);
            $entityManager->flush();
            $responseUser = $userRepository->find($id);
            $response = new JsonResponse(['id' => $responseUser->getId(), 'firstname' => $responseUser->getFirstname(), 'lastname' => $responseUser->getLastname()]);
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