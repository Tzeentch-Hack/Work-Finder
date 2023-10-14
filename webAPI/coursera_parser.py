import requests
from bs4 import BeautifulSoup

import models

search_url = "https://www.coursera.org/search"


def get_parsed_courses(search_params) -> list[models.CourseraCourse]:
    query = search_params
    params = {"query": query}
    response = requests.get(search_url, params=params)
    soup = BeautifulSoup(response.text, "html.parser")
    parsed_courses = []

    if response.status_code == 200:
        course_items = soup.find_all("div", class_='cds-ProductCard-gridCard')
        for course_item in course_items:
            preview_image_url = course_item.find("img")["src"]
            title = course_item.find("div", class_="cds-ProductCard-header").find("h3"). \
                text.encode('latin-1').decode('utf-8')
            sub_title = course_item.find("div", class_="css-oejgx0 cds-ProductCard-partners").find(
                "p").text
            href = course_item.find("div", class_="cds-ProductCard-header").find("a")["href"]
            href = "https://www.coursera.org" + href
            body_content = course_item.find("div", class_="cds-CommonCard-bodyContent")
            content_text = ""
            if body_content is not None:
                content_text = body_content.find("p").text
            content = content_text
            parsed_course = models.CourseraCourse(
                title=title,
                preview_image_url=preview_image_url,
                sub_title=sub_title,
                href=href,
                content=content
            )
            parsed_courses.append(parsed_course)
    else:
        print("Error: Unable to fetch search results")
        raise Exception("Error: Unable to fetch search results")
    return parsed_courses
