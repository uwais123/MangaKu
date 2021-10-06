//
//  MangaItemView.swift
//  iosApp
//
//  Created by Uwais Alqadri on 26/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import KotlinCore
import SDWebImageSwiftUI

struct MangaItemView: View {

  let manga: Manga
  private let extensions = Extensions()

  var body: some View {
    HStack {
      WebImage(url: URL(string: extensions.getPosterImage(manga: manga)))
        .resizable()
        .indicator(.activity)
        .frame(width: 124, height: 200)
        .cornerRadius(12)

      VStack(alignment: .leading) {
        StarsView(manga: manga)

        Text(extensions.getTitle(manga: manga))
          .font(.custom(.mbold, size: 18))
          .lineLimit(2)
          .padding(.top, 5)

        HStack {
          Text(DateFormatterKt.formatDate(dateString: manga.attributes?.startDate ?? "", format: Constants().casualDateFormat))
            .font(.custom(.mbold, size: 12))
            .foregroundColor(.secondary)

          Text("Ch.\(manga.attributes?.chapterCount ?? 0)")
            .font(.custom(.mbold, size: 15))
        }.padding(.top, 5)

        Spacer(minLength: 30)

        Text("Read More")
          .foregroundColor(.white)
          .font(.custom(.mbold, size: 15))
          .padding(13)
          .background(Color.black)
          .cornerRadius(9)
          .padding(.bottom, 10)

      }.padding(.leading, 15)

      Spacer()

    }.padding(.bottom, 30)
  }
}

struct StarsView: View {

  let manga: Manga
  @State var averageRating: Int32 = 0

  private let extensions = Extensions()

  var body: some View {
    HStack {
      ForEach(0..<5) { index in
        Image(systemName: index <= averageRating ? "star.fill" : "star")
          .resizable()
          .frame(width: 15, height: 15)
          .foregroundColor(.yellow)
          .onAppear {
            averageRating = extensions.toFiveStars(avgRating: manga.attributes?.averageRating ?? 0.0)
          }
      }
    }
  }
}
