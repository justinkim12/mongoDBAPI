package api.mongo.nosql.ApiWeb.WebPage.controller;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.Movie.MovieDTO;
import api.mongo.nosql.Domain.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
public class ViewController {

    private final MovieService movieService;

    @GetMapping
    public String home(){
        log.info("Home");
        return "form/index";
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request, Model model) {
        String search = request.getParameter("search");
        if (search != null) {
            List<MovieDTO> movies = movieService.searchByTitle(search);
            log.info("find Movie title = {}",movies.toArray());
            model.addAttribute("movies", movies);
        }
        else{
            model.addAttribute("movies",new ArrayList<>());
        }
        log.info("Search");
        return "form/search";
    }

    @GetMapping("/{movieId}")
    public String moviePage(@PathVariable String movieId, Model model) throws CollectionException {
        MovieDTO movie = movieService.getSingleMovie(movieId);
        model.addAttribute("item", movie);
        return "form/item";
    }
}
