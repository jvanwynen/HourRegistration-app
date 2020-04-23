<?php

namespace App\Controller;

use App\Entity\Company;
use App\Repository\CompanyRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CompanyController extends AbstractController
{
     /**
     * @Route("/company/insert", name="companies")
     * @param Request $request
     * @return Response
     */
    function insertCompany(Request $request, UserRepository $userRepository, CompanyRepository $companyRepository){
        $entityManager = $this->getDoctrine()->getManager();
        $response = new Response();
        // the URI being requested (e.g. /about) minus any query parameters
        $userid = $request->request->get('user_id');
        $companyname = $request->request->get('name');
        $companypassword = $request->request->get('password');
        //Query the database to find the corrosponding company object based on the ID
        $company = new Company();
        $company->setName($companyname);
        $company->setPassword(password_hash($companypassword, PASSWORD_DEFAULT));
        $user = $userRepository->find($userid);
        if($company != null && $user != null){
            // tell Doctrine you want to (eventually) save the Product (no queries yet)
            $entityManager->persist($company);
            // actually executes the queries (i.e. the INSERT query)
            $entityManager->flush();// tell Doctrine you want to (eventually) save the Product (no queries yet)
            $company = $companyRepository->find($company->getId());
            $user->setCompany($company);
            $user->setAdmin(1);
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
     * @Route("/company/getbyid")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return JsonResponse
     */
    function getCompanyById(Request $request, CompanyRepository $companyRepository){
        $response = new JsonResponse(['error' => "No company has been found with this ID"]);
        $companyid = $request->query->get('id');
        $company = $companyRepository->find($companyid);
        if ($company != null) {
            // the data to send when creating the response
            $response = new JsonResponse(['id' => $company->getId(), 'name' => $company->getName()]);
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
     * @Route("/company/allcompanies")
     * @param CompanyRepository $companyRepository
     * @return JsonResponse
     */
    function getAllCompanies(CompanyRepository $companyRepository)
    {
        $response = new JsonResponse(['error' => "No companies has been found"]);
        $companies = $companyRepository->findAll();
        if ($companies != null) {
            $companyArray = array();
            foreach ($companies as $item) {
                $companyArray[] = array(
                    'id' => $item->getId(),
                    'companyname' => $item->getName(),
                );
            }
            $response = new JsonResponse($companyArray);
            $response->setStatusCode(Response::HTTP_OK);
            $response->headers->set('Content-Type', 'application/json');
            return $response;
        }
        $response->setStatusCode(Response::HTTP_NOT_FOUND);
        $response->headers->set('Content-Type', 'application/json');
        return $response;
    }


    /**
     * @Route("/company/deletebyid")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return Response
     */
    function deleteCompanyById(Request $request, CompanyRepository $companyRepository){
        $entityManager = $this->getDoctrine()->getManager();
        $companyid = $request->request->get('id');
        $companyToDelete = $companyRepository->find($companyid);
        if($companyToDelete != null){
            $entityManager->remove($companyToDelete);
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
     * @Route("/company/updatebyid")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return JsonResponse|Response
     */
    function updateById(Request $request, CompanyRepository $companyRepository){
        $entityManager = $this->getDoctrine()->getManager();
        // the URI being requested (e.g. /about) minus any query parameters
        $id = $request->request->get('id');
        $companyname = $request->request->get('companyname');
        $companypassword = $request->request->get('companypassword');
        $company = $companyRepository->find($id);
        if($company != null) {
            $company->setName($companyname);
            $company->setPassword(password_hash($companypassword, PASSWORD_DEFAULT));
            $entityManager->persist($company);
            $entityManager->flush();
            $responseCompany = $companyRepository->find($id);
            $response = new JsonResponse(['id' => $responseCompany->getId(), 'companyname' => $responseCompany->getName()]);
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
     * @Route("/company/validate")
     * @param Request $request
     * @param CompanyRepository $companyRepository
     * @return JsonResponse
     */
    function validateCompanyPassword(Request $request, CompanyRepository $companyRepository){
        $response = new JsonResponse(['error' => "Wrong password"]);
        $companyid = $request->request->get('id');
        $insertedCompanyPassword = $request->request->get('companypassword');
        $company = $companyRepository->find($companyid);
        $companypassword = $company->getPassword();
        if (password_verify($insertedCompanyPassword, $companypassword)) {
            // the data to send when creating the response
            $response = new JsonResponse("success");
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
