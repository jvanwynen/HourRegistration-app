<?php

namespace App\Controller;

require_once 'C:\Users\joost\Documents\projects\HourRegistration-app\Back-end\HourRegistrationAPI\vendor\autoload.php';

use Google_Client;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class TokenController extends AbstractController
{
    /**
     * @Route("/token/verify", name="token")
     * @param Request $request
     * @return JsonResponse
     */
    public function validateToken(Request $request)
    {

        $CLIENT_ID = "627510897874-46pejgnail9p51tkib5hg9d58nv9r85p.apps.googleusercontent.com";
        $response = new JsonResponse();
        $id_token = $request->request->get('id_token');

//      Get $id_token via HTTPS POST.

        $client = new Google_Client(['client_id' => $CLIENT_ID]);  // Specify the CLIENT_ID of the app that accesses the backend
        echo $client->getClientId();
        $payload = $client->verifyIdToken($id_token);
        if ($payload) {
            $userid = $payload['sub'];
            // If request specified a G Suite domain:
            //$domain = $payload['hd'];
            echo $userid;
            return $response;
        } else {
            // Invalid ID token
            echo false;
            return $response;
        }
    }
}
