package lv.venta.cbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.cbs.service.MovieService;
import lv.venta.cbs.service.ShowtimeService;

@Controller
@RequestMapping("/booking")
public class BookingController {
	
	private final MovieService movieService;

    @Autowired
    public BookingController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        //this.showtimeService = showtimeService;
    }
}
