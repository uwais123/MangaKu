//
//  BrowseView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 24/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct BrowseView: View {

  @ObservedObject var viewModel: BrowseViewModel
  @ObservedObject var mangaViewModel: MyMangaViewModel
  private let assembler = AppAssembler()

  let genres: [Genre] = [
    Genre(name: "Shonen", image: "imgShonen", query: "shonen", font: .sedgwickave),
    Genre(name: "Seinen", image: "imgSeinen", query: "seinen", font: .mashanzheng),
    Genre(name: "Shojo", image: "imgShojo", query: "shojo", font: .sedgwickave)
  ]

  var body: some View {
    NavigationView {
      ScrollView(.vertical, showsIndicators: false) {
        VStack(alignment: .leading) {
          Text("Genre")
            .font(.custom(.msemibold, size: 15))
            .padding(.horizontal, 17)

          ScrollView(.horizontal, showsIndicators: false) {
            HStack {
              ForEach(genres, id: \.id) { genre in
                GenreView(genre: genre)
              }
            }.padding(.leading, 13)
          }

          Text("Trending Now")
            .font(.custom(.msemibold, size: 15))
            .padding(.leading, 17)
            .padding(.top, 30)


          if viewModel.loading {
            VStack {
              ForEach(0..<10) { _ in
                ShimmerBrowseView()
              }
            }.padding(.leading, 17)
            .padding(.trailing, 30)
            .padding(.bottom, 100)
          } else {
            VStack {
              ForEach(viewModel.trendingManga, id: \.id) { manga in
                NavigationLink(destination: DetailView(viewModel: assembler.resolve(), mangaId: manga.id)) {
                  MangaItemView(manga: manga) { item in
                    mangaViewModel.addFavoriteManga(manga: item) {
                      print("saved")
                    }
                  }
                }.buttonStyle(PlainButtonStyle())
              }
            }.padding(.leading, 17)
            .padding(.trailing, 30)
            .padding(.bottom, 100)
          }
        }.padding(.top, 30)
      }
      .navigationBarTitle("Browse")
      .navigationBarItems(
        trailing: NavigationLink(destination: SearchView(viewModel: assembler.resolve())) {
          Image(systemName: "magnifyingglass")
            .resizable()
            .foregroundColor(.black)
            .frame(width: 20, height: 20)
        }
      )
    }
  }
}
