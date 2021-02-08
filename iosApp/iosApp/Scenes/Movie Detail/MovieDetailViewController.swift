//
//  MovieDetailViewController.swift
//  iosApp
//
//  Created by Boran Aslan on 1.02.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared
import Longinus

class MovieDetailViewController: UIViewController {

    @IBOutlet var posterImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var ratingLabel: UILabel!
    @IBOutlet var overviewLabel: UILabel!
    
    var movieId: Int32 = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if movieId > 0 {
            fetchMovieDetail()
        }
    }
    
    private func fetchMovieDetail() {
        let movieApiClient = TMDbApiClient()
        movieApiClient.fetchMovieDetail(movieId: movieId) { (movie, error) in
            if let movie = movie {
                self.setMovieInfo(movie: movie)
            } else {
                print(error?.localizedDescription ?? "error")
            }
        }
    }
    
    private func setMovieInfo(movie: MovieModel) {
        let url = URL(string: Constants.Companion().moviePosterUrl + movie.posterPath)
        posterImageView.lg.setImage(with: url)
        titleLabel.text = movie.title
        ratingLabel.text = String(movie.voteAverage)
        overviewLabel.text = movie.overview
    }
    
}
