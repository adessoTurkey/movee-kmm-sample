//
//  MovieListViewController.swift
//  iosApp
//
//  Created by Boran Aslan on 1.02.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared
import Longinus

class MovieListViewController: UIViewController {
    
    @IBOutlet var movieTableView: UITableView!
    
    private var movies: [MovieModel] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Movee"
        
        fetchPopularMovies()
    }
    
    private func fetchPopularMovies() {
        let movieApiClient = TMDbApiClient()
        movieApiClient.fetchPopularMovies { (popularMovies, error) in
            if let movies = popularMovies?.results {
                self.movies = movies
                self.movieTableView.reloadData()
            } else {
                print(error?.localizedDescription ?? "error")
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showdetail" {
            let movieDetailViewController = segue.destination as! MovieDetailViewController
            if let cell = sender as? UITableViewCell, let indexPath = movieTableView.indexPath(for: cell) {
                movieDetailViewController.movieId = movies[indexPath.row].id
            }
        }
    }
    
}

extension MovieListViewController: UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return movies.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "MovieTableViewCell",
                                                       for: indexPath) as? MovieTableViewCell else {
                                                        fatalError("Cast failed in cellForRowAt to \(MovieTableViewCell.self)")
        }
        
        let movie = movies[indexPath.row]
        
        let url = URL(string: Constants.Companion().moviePosterUrl + movie.posterPath )
        cell.posterImageView.lg.setImage(with: url)
        cell.titleLabel.text = movie.title
        cell.ratingLabel.text = String(movie.voteAverage)
        
        return cell
    }
    
}
