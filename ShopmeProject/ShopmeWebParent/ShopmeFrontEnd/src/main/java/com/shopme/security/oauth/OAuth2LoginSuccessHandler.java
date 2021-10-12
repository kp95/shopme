package com.shopme.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Customer;
import com.shopme.customer.CustomerService;
@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Autowired
	private CustomerService custmerService;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomerOAuth2User auth2User = (CustomerOAuth2User) authentication.getPrincipal();
		
		String name = auth2User.getName();
		String email = auth2User.getEmail();
		String countryCode = request.getLocale().getCountry();
		
		Customer customer = custmerService.getCustomerByEmail(email);
		if(customer == null) {
			custmerService.addNewCustomerUponOAuthLogin(name,email,countryCode);
		}
		else {
			custmerService.updateAuthenticationType(customer, AuthenticationType.GOOGLE);
		}
		
		//System.err.println(name + "   -- " + email);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
