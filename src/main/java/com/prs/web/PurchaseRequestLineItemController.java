package com.prs.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping("/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository prliRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllPurchaseRequestLineItems() {
		try {
			return JsonResponse.getInstance(prliRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Purchase request line item list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
		try {
			Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
			if (prli.isPresent())
				return JsonResponse.getInstance(prli.get());
			else
				return JsonResponse.getErrorInstance("Purchase request line item not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting purchase request line item:  " + e.getMessage(), null);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		return savePurchaseRequestLineItem(prli);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		return savePurchaseRequestLineItem(prli);
	}

	private @ResponseBody JsonResponse savePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			prliRepository.save(prli);
			return JsonResponse.getInstance(prli);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			prliRepository.delete(prli);
			return JsonResponse.getInstance(prli);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}


//package com.prs.web;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequestLineItem;
//import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin	// Prevents errors such as 'Response for preflight has invalid HTTP status code 403.'
//@Controller		
//@RequestMapping(path="/PurchaseRequestLineItems")
//public class PurchaseRequestLineItemController extends BaseController {
//	@Autowired
//	private PurchaseRequestLineItemRepository prliRepository;
//
//	@GetMapping(path="/List")
//	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems() {
//		return prliRepository.findAll();
//	}
//	
//	@GetMapping(path="/Get")
//	public @ResponseBody List<PurchaseRequestLineItem> getPurchaseRequestLineItem(@RequestParam int id) {
//		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
//		return getReturnArray(prli);
//	}
//
//	@PostMapping(path="/Add") 
//	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
//		try {
//			prliRepository.save(prli);
//			return PRSMaintenanceReturn.getMaintReturn(prli);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return PRSMaintenanceReturn.getMaintReturnError(prli, e.getMessage());
//		}
//	}
//	
//	@GetMapping(path="/Remove") // Map ONLY GET Requests
//	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequestLineItem (@RequestParam int id) {
//		
//		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
//		try {
//			prliRepository.delete(prli.get());
//			return PRSMaintenanceReturn.getMaintReturn(prli.get());
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, e.toString());
//		}
//		
//	}
//
//	@PostMapping(path="/Change") 
//	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
//		try {
//			prliRepository.save(prli);
//			return PRSMaintenanceReturn.getMaintReturn(prli);
//		}
//		catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
//		}
//		catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, e.toString());
//		}
//		
//	}
//}



//package com.prs.web;
//
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
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.prs.business.purchaserequest.PurchaseRequestLineItem;
//import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
//import com.prs.util.JsonResponse;
//
//@Controller
//@RequestMapping("/PurchaseRequests")
//public class PurchaseRequestLineItemController {
//	
//	@Autowired
//	private PurchaseRequestLineItemRepository prliRepository;
//	
//	@GetMapping("/List")
//	public @ResponseBody JsonResponse getAllPurchaseRequestLineItems() {
//		try {	
//			return JsonResponse.getInstance(prliRepository.findAll());
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Purchase request line item list failure:" + e.getMessage(), e);
//		}
//	}
//	
//	@GetMapping("/Get/{id}")
//	public @ResponseBody JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
//		try {
//			Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
//			if (prli.isPresent())
//				return JsonResponse.getInstance(prli.get());
//			else
//				return JsonResponse.getErrorInstance("Purchase request line item not found for id: " + id, null);
//		}
//		catch (Exception e) {
//			return JsonResponse.getErrorInstance("Error getting purchase request line item:  " + e.getMessage(), null);
//		}
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
//		return addPurchaseRequestLineItem(prli);
//	}
//
//	@PostMapping("/Change")
//	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
//		return updatePurchaseRequestLineItem(prli);
//	}
//
//	private @ResponseBody JsonResponse savePurchaseRequest(@RequestBody PurchaseRequestLineItem prli) {
//		try {
//			prliRepository.save(prli);
//			return JsonResponse.getInstance(prli);
//		} catch (DataIntegrityViolationException ex) {
//			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody JsonResponse removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
//		try {
//			prliRepository.delete(prli);
//			return JsonResponse.getInstance(prli);
//		} catch (Exception ex) {
//			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
//		}
//	}
//	
//}




//package com.prs.web;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import javax.servlet.http.HttpServletRequest;
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
//import com.prs.business.product.Product;
//import com.prs.business.purchaserequest.PurchaseRequest;
//import com.prs.business.purchaserequest.PurchaseRequestLineItem;
//import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
//import com.prs.business.purchaserequest.PurchaseRequestRepository;
//import com.prs.util.PRSMaintenanceReturn;
//
//@CrossOrigin
//@Controller
//@RequestMapping(path = "/PurchaseRequestLineItems")
//public class PurchaseRequestLineItemController extends BaseController {
//	@Autowired
//	private PurchaseRequestLineItemRepository prliRepository;
//	@Autowired
//	private PurchaseRequestRepository prRepository;
//	
//	@GetMapping(path = "/Get")
//	public @ResponseBody List<PurchaseRequestLineItem> getPurchaseRequestLineItem (@RequestParam int id) {
//
//		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
//		return getReturnArray(prli);
//	}
// 
//	@GetMapping(path = "/List")
//	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllLineItems() {
//		return prliRepository.findAll();
//	}
//	
//	@GetMapping(path = "/LinesForPR")
//	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllLineItemsForPR(@RequestParam int id) {
//		return prliRepository.findAllByPurchaseRequestId(id);
//	}
//	
//	@PostMapping(path = "/Add")
//	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli,
//			HttpServletRequest req) {
//		PRSMaintenanceReturn ret = savePRLI(prli);
//		// only continue if save was successful
//		if (ret.getResult().equals(PRSMaintenanceReturn.SUCCESS)) {
//			String msg = "";
//			try {
//				updateRequestTotal(prli);
//				ret = PRSMaintenanceReturn.getMaintReturn(prli);
//			} catch (Exception e) {
//				msg = e.getMessage();
//				e.printStackTrace();
//				ret = PRSMaintenanceReturn.getMaintReturnError(prli, msg);
//			}
//		}
//		return ret;
//		
//	}
//	
//	@PostMapping(path = "/Change") 
//	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
//		try {
//			prliRepository.save(prli);
//			// now update the PR total
//			updateRequestTotal(prli);
//		}
//		catch (Exception e) {
//
//			prli = null;
//		}
//		
//		return PRSMaintenanceReturn.getMaintReturn(prli);
//	}
//	
//	@GetMapping(path = "/Remove") 
//	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequestLineItem (@RequestParam int id) {
//		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
//		try {
//			prliRepository.delete(prli.get());
//			// now update the PR total
//			updateRequestTotal(prli.get());
//		} catch (Exception e) {
//			prli = null;
//		}
//		return PRSMaintenanceReturn.getMaintReturn(prli);
//	}
//	
//	private void updateRequestTotal(PurchaseRequestLineItem prli) throws Exception {
//		Optional<PurchaseRequest> prOpt = prRepository.findById(prli.getPurchaseRequest().getId());
//		
//		PurchaseRequest pr = prOpt.get();
//		List<PurchaseRequestLineItem> lines = new ArrayList<>();
//		lines = prliRepository.findAllByPurchaseRequestId(pr.getId());
//		double total = 0;
//		for (PurchaseRequestLineItem line: lines) {
//			Product p = line.getProduct();
//			double lineTotal = line.getQuantity()*p.getPrice();
//			total += lineTotal;
//		}
//		pr.setTotal(total);
//		prRepository.save(pr);
//	
//	}
//
//	public @ResponseBody PRSMaintenanceReturn savePRLI (@RequestBody PurchaseRequestLineItem prli) {
//
//		try {
//			prliRepository.save(prli);
//			return PRSMaintenanceReturn.getMaintReturn(prli);
//		} catch (DataIntegrityViolationException dive) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
//		} catch (Exception e) {
//			return PRSMaintenanceReturn.getMaintReturnError(prli, e.toString());
//		}
//
//	}
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
//import com.prs.business.purchaserequest.PurchaseRequestLineItem;
//import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
//
//@Controller
//@RequestMapping("/PurchaseRequestLineItems")
//public class PurchaseRequestLineItemController {
//	@Autowired
//	private PurchaseRequestLineItemRepository prliRepository; // variable that calls out CRUD methods
//	
//	@GetMapping("/List")
//	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems() {
//		Iterable<PurchaseRequestLineItem> purchaserequestlineitems = prliRepository.findAll();
//		return purchaserequestlineitems; // return in iterable list of vendors		
//	}
//	
//	@GetMapping("/Get")
//	public @ResponseBody Optional<PurchaseRequestLineItem> getPurchaseRequestLineItem(@RequestParam int id) {
//		Optional<PurchaseRequestLineItem> purchaserequestlineitem = prliRepository.findById(id);
//		return purchaserequestlineitem; // return in iterable list of vendors		
//	}
//	
//	@PostMapping("/Add")
//	public @ResponseBody PurchaseRequestLineItem addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaserequestlineitem) {
//		return prliRepository.save(purchaserequestlineitem);
//	}
//	
//	@PostMapping("/Change")
//	public @ResponseBody PurchaseRequestLineItem updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaserequestlineitem) {
//		return prliRepository.save(purchaserequestlineitem);
//	}
//	
//	@PostMapping("/Remove")
//	public @ResponseBody String removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaserequestlineitem) {
//		prliRepository.delete(purchaserequestlineitem);
//		return "purchase request line item deleted";
//	}
//}
