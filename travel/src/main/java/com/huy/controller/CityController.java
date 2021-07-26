package com.huy.controller;

import com.huy.model.City;
import com.huy.model.Country;
import com.huy.service.CityService;
import com.huy.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @ModelAttribute("countries")
    public Iterable<Country> countries() {
        return countryService.findAll();
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/cities/index");
        modelAndView.addObject("cities", cityService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("city", new City());
        return "/cities/create";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("city") City city, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "/cities/create";
        }
        cityService.save(city);
        redirect.addFlashAttribute("message", "Thêm Thành Phố " + city.getName() + " Thành Công!");
        return "redirect:/cities";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            redirect.addFlashAttribute("message", "Không Tìm Thấy Thành Phố Ở Vị Trí Số " + id + " Trong Danh Sách!");
            return "redirect:/cities";
        }
        model.addAttribute("city", city.get());
        return "/cities/showDetails";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            redirect.addFlashAttribute("message", "Không Tìm Thấy Thành Phố Ở Vị Trí Số " + id + " Trong Danh Sách!");
            return "redirect:/cities";
        }
        model.addAttribute("city", city.get());
        return "/cities/edit";
    }

    @PostMapping("/edit")
    public String edit(@Validated @ModelAttribute("city") City city, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "/cities/edit";
        }
        cityService.save(city);
        redirect.addFlashAttribute("message", "Cập Nhật Thành Phố " + city.getName() + " Thành Công!");
        return "redirect:/cities";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            redirect.addFlashAttribute("message", "Không Tìm Thấy Thành Phố Ở Vị Trí Số " + id + " Trong Danh Sách!");
            return "redirect:/cities";
        }
        model.addAttribute("city", city.get());
        return "/cities/delete";
    }

    @PostMapping("/delete")
    public String edit(@ModelAttribute("city") City city, RedirectAttributes redirect) {
        Optional<City> oldCity = cityService.findById(city.getId());
        if (!oldCity.isPresent()) {
            redirect.addFlashAttribute("message", "Không Tìm Thấy Thành Phố Ở Vị Trí Số " + city.getId() + " Trong Danh Sách!");
            return "redirect:/cities";
        }
        redirect.addFlashAttribute("message", "Xóa Thành Phố " + city.getName() + " Thành Công!");
        cityService.deleteById(city.getId());
        return "redirect:/cities";
    }

}
