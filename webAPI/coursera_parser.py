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
            parsed_course = models.CourseraCourse
            parsed_course.preview_image_url = course_item.find("img")["src"]
            parsed_course.title = course_item.find("div", class_="cds-ProductCard-header").find("h3"). \
                text.encode('latin-1').decode('utf-8')
            parsed_course.sub_title = course_item.find("div", class_="css-oejgx0 cds-ProductCard-partners").find(
                "p").text
            parsed_course.href = course_item.find("div", class_="cds-ProductCard-header").find("a")["href"]
            parsed_course.href = "https://www.coursera.org" + parsed_course.href
            body_content = course_item.find("div", class_="cds-CommonCard-bodyContent")
            content_text = ""
            if body_content is not None:
                content_text = body_content.find("p").text
            parsed_course.content = content_text
            parsed_courses.append(parsed_course)
    else:
        print("Error: Unable to fetch search results")
        raise Exception("Error: Unable to fetch search results")
    return parsed_courses
