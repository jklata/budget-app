package pl.jklata.budgetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jklata.budgetapp.service.AdminService;

/**
 * @author Jakub Klata, Pentacomp Systemy Informatyczne S.A.
 */

@Slf4j
@RequestMapping(value = "/admin")
@Secured("ROLE_ADMIN")
@Controller
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "")
    public String getAdminBoard() {
        return "admin/adminBoard";
    }


    @GetMapping(value = "/users")
    public String listPayments(
            ModelMap model, @SortDefault("id") Pageable pageable) {

        model.addAttribute("page", adminService.findPaginated(pageable));

        return "admin/users";
    }

}
