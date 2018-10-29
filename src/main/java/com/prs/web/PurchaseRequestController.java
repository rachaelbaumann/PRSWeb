package com.prs.web;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.util.JsonResponse;
import com.prs.util.PRSMaintenanceReturn;

@CrossOrigin
@Controller
@RequestMapping("/PurchaseRequests")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository prRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllPurchaseRequests() {
		try {
			return JsonResponse.getInstance(prRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Purchase request list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
		try {
			Optional<PurchaseRequest> pr = prRepository.findById(id);
			if (pr.isPresent())
				return JsonResponse.getInstance(pr.get());
			else
				return JsonResponse.getErrorInstance("Purchase request not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting purchase request:  " + e.getMessage(), null);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addPurchaseRequest(@RequestBody PurchaseRequest pr) {
		pr.setSubmittedDate(LocalDateTime.now());
		pr.setStatus(PurchaseRequest.STATUS_NEW);
		return savePurchaseRequest(pr);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequest pr) {
		return savePurchaseRequest(pr);
	}

	private @ResponseBody JsonResponse savePurchaseRequest(@RequestBody PurchaseRequest pr) {
		try {
			prRepository.save(pr);
			return JsonResponse.getInstance(pr);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequest(@RequestBody PurchaseRequest pr) {
		try {
			prRepository.delete(pr);
			return JsonResponse.getInstance(pr);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
	
	@PostMapping("/Submit")
	public @ResponseBody JsonResponse submitForReview (@RequestBody PurchaseRequest pr) {
		if (pr.getTotal() <= 50) {
			pr.setStatus(PurchaseRequest.STATUS_APPROVED);
		} else {
			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
		}
		pr.setSubmittedDate(LocalDateTime.now());
		return savePurchaseRequest(pr);
	}
	
	@PostMapping("/Approve")
	public @ResponseBody JsonResponse approveRequest (@RequestBody PurchaseRequest pr) {
		pr.setStatus(PurchaseRequest.STATUS_APPROVED);
		return savePurchaseRequest(pr);
	}
	
	@PostMapping("/Reject") 
	public @ResponseBody JsonResponse rejectRequest (@RequestBody PurchaseRequest pr) {
		pr.setStatus(PurchaseRequest.STATUS_REJECTED);
		return savePurchaseRequest(pr);
	}
	
	@GetMapping("/ListReview")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequestsForReview(@RequestParam int id) {
		Iterable<PurchaseRequest> reviewPurchaseRequests = prRepository.findAllByUserIDNotAndStatus(id, "Review");
		return reviewPurchaseRequests;
	}
	
}




//package com.prs.web;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequest;
//import com.prs.business.purchaserequest.PurchaseRequestRepository;
//import com.prs.util.JsonResponse;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
//@Controller		
//@RequestMapping(path="/PurchaseRequests")
//public class PurchaseRequestController extends BaseController {
//	@Autowired
//	private PurchaseRequestRepository prRepository;
//
////	@GetMapping(path="/List")
////	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
////		return prRepository.findAll();
////	}
////	
////	@GetMapping(path="/Get")
////	public @ResponseBody List<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
////		Optional<PurchaseRequest> pr = prRepository.findById(id);
////		return getReturnArray(pr);
////	}
//	
//	@GetMapping("/List")
//    public @ResponseBody JsonResponse getAllPurchaseRequests() {
//        try {
//            return JsonResponse.getInstance(prRepository.findAll());
//        } catch (Exception e) {
//            return JsonResponse.getErrorInstance("Purchase request list failure:" + e.getMessage(), e);
//        }
//    }
//
//    @GetMapping("/Get/{id}")
//    public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
//        try {
//            Optional<PurchaseRequest> purchaseRequest = prRepository.findById(id);
//            if (purchaseRequest.isPresent())
//                return JsonResponse.getInstance(purchaseRequest.get());
//            else
//                return JsonResponse.getErrorInstance("Purchase request not found for id: " + id, null);
//        } catch (Exception e) {
//            return JsonResponse.getErrorInstance("Error getting purchase request:  " + e.getMessage(), null);
//        }
//    }
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest (@RequestBody PurchaseRequest pr) {
//		try {
//			prRepository.save(pr);
//			return PRSMaintenanceReturn.getMaintReturn(pr);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return PRSMaintenanceReturn.getMaintReturnError(pr, e.getMessage());
//		}
//	}
//	
//	@GetMapping(path="/Remove") // Map ONLY GET Requests
//	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequest (@RequestParam int id) {
//		
//		Optional<PurchaseRequest> pr = prRepository.findById(id);
//		try {
//			prRepository.delete(pr.get());
//			return PRSMaintenanceReturn.getMaintReturn(pr.get());
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, e.toString());
//		}
//		
//	}
//
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequest (@RequestBody PurchaseRequest pr) {
//		try {
//			prRepository.save(pr);
//			return PRSMaintenanceReturn.getMaintReturn(pr);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, e.toString());
//		}	
//	}
//	
//	
//	@PostMapping(path = "/Submit")
//	public @ResponseBody PRSMaintenanceReturn submitForReview (@RequestBody PurchaseRequest pr) {
//		if (pr.getTotal() <= 50) {
//			pr.setStatus(PurchaseRequest.STATUS_APPROVED);
//		} else {
//			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
//		}
//		pr.setSubmittedDate(LocalDateTime.now());
//		return updatePurchaseRequest(pr);
//	}
//}



//package com.prs.web;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequest;
//import com.prs.business.purchaserequest.PurchaseRequestRepository;
//import com.prs.util.JsonResponse;
//import com.prs.util.PRSMaintenanceReturn;
//
//
//@Controller
//@RequestMapping("/PurchaseRequests")
//public class PurchaseRequestController {
//	@Autowired
//	private PurchaseRequestRepository purchaserequestRepository; // variable that calls out CRUD methods
//	
//	@GetMapping("/List")
//	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
//		Iterable<PurchaseRequest> purchaserequests = purchaserequestRepository.findAll();
//		return purchaserequests; // return in iterable list of vendors		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
//		Optional<PurchaseRequest> purchaserequest = purchaserequestRepository.findById(id);
//		return purchaserequest; // return in iterable list of vendors		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody PurchaseRequest addPurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		return purchaserequestRepository.save(purchaserequest);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody PurchaseRequest updatePurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		return purchaserequestRepository.save(purchaserequest);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removePurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		purchaserequestRepository.delete(purchaserequest);
//		return "purchase request deleted";
//	}
//}

//@Controller
//@RequestMapping("/PurchaseRequests")
//public class PurchaseRequestController {
//	
//	@Autowired
//	private PurchaseRequestRepository prRepository;
//	
//	@GetMapping("/List")
//	public @ResponseBody JsonResponse getAllPurchaseRequests() {
//		try {	
//			return JsonResponse.getInstance(prRepository.findAll());
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Purchase request list failure:" + e.getMessage(), e);
//		}
//	}
//	
//	@GetMapping("/Get/{id}")
//	public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
//		try {
//			Optional<PurchaseRequest> pr = prRepository.findById(id);
//			if (pr.isPresent())
//				return JsonResponse.getInstance(pr.get());
//			else
//				return JsonResponse.getErrorInstance("Purchase request not found for id: " + id, null);
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error getting purchase request:  " + e.getMessage(), null);
//		}
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody JsonResponse addPurchaseRequest(@RequestBody PurchaseRequest pr) {
//		return addPurchaseRequest(pr);
//	}
//
//	@PostMapping("/Change")
//	public @ResponseBody JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequest pr) {
//		return updatePurchaseRequest(pr);
//	}
//
//	private @ResponseBody JsonResponse savePurchaseRequest(@RequestBody PurchaseRequest pr) {
//		try {
//			prRepository.save(pr);
//			return JsonResponse.getInstance(pr);
//		} catch (DataIntegrityViolationException ex) {
//			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody JsonResponse removePurchaseRequest(@RequestBody PurchaseRequest pr) {
//		try {
//			prRepository.delete(pr);
//			return JsonResponse.getInstance(pr);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping(path = "/SubmitForReview") 
//	public @ResponseBody JsonResponse submitForReview (@RequestBody PurchaseRequest pr) {
//		if (pr.getTotal() <= 50)
//			pr.setStatus(PurchaseRequest.STATUS_APPROVED);
//		else
//			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
//		pr.setSubmittedDate(LocalDateTime.now());
//		return savePurchaseRequest(pr);
//	}
//	
//	@PostMapping(path = "/Approve")
//	public @ResponseBody JsonResponse approvePurchaseRequest (@RequestBody PurchaseRequest pr) {
//		
//		return null;
//		
//	}
//	
//}







//package com.prs.web;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequest;
//import com.prs.business.purchaserequest.PurchaseRequestRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//
//@CrossOrigin
//@Controller
//@RequestMapping(path = "/PurchaseRequests")
//public class PurchaseRequestController extends BaseController {
//	@Autowired
//	private PurchaseRequestRepository purchaserequestRepository;
//	
//	@GetMapping(path = "/Get") 
//	public @ResponseBody List<PurchaseRequest> getPurchaseRequest (@RequestParam int id) {
//		Optional<PurchaseRequest> pr = purchaserequestRepository.findById(id);
//		return getReturnArray(pr.get());
//	}
//
//	@GetMapping(path = "/List")
//	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
//		// This returns a JSON or XML with the users
//		Iterable<PurchaseRequest> allPRs = purchaserequestRepository.findAll();
//		return allPRs;
//	}
//	
//	@GetMapping(path = "/ListReview")
//	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequestsForReview(@RequestParam int id) {
//		Iterable<PurchaseRequest> reviewPRs = purchaserequestRepository.findAllByUserIdNotAndStatus(id, "Review");
//		return reviewPRs;
//	}
//	
//	@PostMapping(path = "/Add")
//	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest (@RequestBody PurchaseRequest purchaseRequest) {
//		//Timestamp ts = new Timestamp(System.currentTimeMillis());
//		//Defaulting submittedDate to current date.  Will be reset once line items are finalized.
//		LocalDateTime ts = LocalDateTime.now();
//		purchaseRequest.setSubmittedDate(ts);
//		//Default status to 'new' as a PR will always be 'new' on initial add.
//		purchaseRequest.setStatus(PurchaseRequest.STATUS_NEW);
//		return savePR(purchaseRequest);
//	}
//
//	@PostMapping(path = "/Change") 
//	public @ResponseBody PRSMaintenanceReturn updateUser (@RequestBody PurchaseRequest purchaseRequest) {
//		return savePR(purchaseRequest);
//	}
//
//	@GetMapping(path = "/Remove") 
//	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequest (@RequestParam int id) {
//		Optional<PurchaseRequest> purchaseRequest = purchaserequestRepository.findById(id);
//		try {
//			purchaserequestRepository.delete(purchaseRequest.get());
//		}
//		catch (Exception e) {
//			purchaseRequest = null;
//		}
//		return PRSMaintenanceReturn.getMaintReturn(purchaseRequest.get());
//	}
//
//	/*
//	 *  Business rules for submitting for review:
//	 *  - Update the submittedDate to current date
//	 *  - Update the status to 'review', unless total is <= $50 then set to 'approved'
//	 */
//	@PostMapping(path = "/SubmitForReview") 
//	public @ResponseBody PRSMaintenanceReturn submitForReview (@RequestBody PurchaseRequest pr) {
//		if (pr.getTotal() <= 50)
//			pr.setStatus(PurchaseRequest.STATUS_APPROVED);
//		else
//			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
//		pr.setSubmittedDate(LocalDateTime.now());
//		return savePR(pr);
//	}
//	
//	@PostMapping(path = "/ApprovePR") 
//	public @ResponseBody PRSMaintenanceReturn approvePR (@RequestBody PurchaseRequest pr) {
//		pr.setStatus(PurchaseRequest.STATUS_APPROVED);
//		return savePR(pr);
//	}
//
//	@PostMapping(path = "/RejectPR") 
//	public @ResponseBody PRSMaintenanceReturn rejectPR (@RequestBody PurchaseRequest pr) {
//		pr.setStatus(PurchaseRequest.STATUS_REJECTED);
//		return savePR(pr);
//	}
//
//	public @ResponseBody PRSMaintenanceReturn savePR (@RequestBody PurchaseRequest pr) {
//
//		try {
//			purchaserequestRepository.save(pr);
//			return PRSMaintenanceReturn.getMaintReturn(pr);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(pr, e.toString());
//		}
//
//	}
//
//}



// OG CODE
//package com.prs.web;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequest;
//import com.prs.business.purchaserequest.PurchaseRequestRepository;
//
//@Controller
//@RequestMapping("/PurchaseRequests")
//public class PurchaseRequestController {
//	@Autowired
//	private PurchaseRequestRepository prRepository; // variable that calls out CRUD methods
//	
//	@GetMapping("/List")
//	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
//		Iterable<PurchaseRequest> purchaserequests = prRepository.findAll();
//		return purchaserequests; // return in iterable list of vendors		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
//		Optional<PurchaseRequest> purchaserequest = prRepository.findById(id);
//		return purchaserequest; // return in iterable list of vendors		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody PurchaseRequest addPurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		return prRepository.save(purchaserequest);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody PurchaseRequest updatePurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		return prRepository.save(purchaserequest);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removePurchaseRequest(@RequestBody PurchaseRequest purchaserequest) {
//		prRepository.delete(purchaserequest);
//		return "purchase request deleted";
//	}
//}
