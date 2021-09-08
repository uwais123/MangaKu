//
//  MyMangaViewModel.swift
//  iosApp
//
//  Created by Uwais Alqadri on 24/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import KotlinCore
import Combine
import KMPNativeCoroutinesCombine

class MyMangaViewModel: ObservableObject {

  @Published var mangas = [Manga]()
  @Published var loading = false
  @Published var errorMessage = ""

  private let favoriteUseCase: GetMangaFavoriteUseCase
  private var cancellables = Set<AnyCancellable>()

  init(favoriteUseCase: GetMangaFavoriteUseCase) {
    self.favoriteUseCase = favoriteUseCase
  }

  func fetchFavoriteManga() {
    loading = true
    createPublisher(for: favoriteUseCase.invokeNative())
      .receive(on: DispatchQueue.main)
      .sink { completion in
        switch completion {
        case .finished:
          self.loading = false
        case .failure(let error):
          self.errorMessage = error.localizedDescription
        }
      } receiveValue: { value in
        self.mangas = value
      }.store(in: &cancellables)
  }
}
