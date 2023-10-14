from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By

def setup_driver():
    """
    Setup and return a Chrome WebDriver instance with specified options.

    Options:
    1. Run chrome in headless mode (without GUI).
    2. Bypass OS security model.
    3. Overcome limited resource problems.

    Returns:
        driver (webdriver.Chrome): Configured Chrome WebDriver instance.
    """
    options = webdriver.ChromeOptions()
    options.add_argument("--headless")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-gpu")
    options.add_argument("--disable-dev-shm-usage")

    # Set path to chromedriver as per your configuration
    webdriver_service = Service(ChromeDriverManager().install())

    return webdriver.Chrome(service=webdriver_service, options=options)




def remove_duplicates(text):
    lines = text.split('\n')
    unique_lines = []
    seen = set()

    for line in lines:
        if line not in seen:
            unique_lines.append(line)
            seen.add(line)

    return '\n'.join(unique_lines)


import re


def remove_cookie_info(text):
    cookie_phrases = [
        'Always Active',
        'Performance Cookies',
        'Functional Cookies',
        'Targeting Cookies',
        'Social Media Cookies'
    ]

    for phrase in cookie_phrases:
        text = re.sub(phrase + '.*?(?=' + '|'.join(cookie_phrases) + '|$)', '', text, flags=re.DOTALL)

    return text


def clean_text(text):
    text = remove_duplicates(text)
    text = remove_cookie_info(text)

    text = re.sub(r'[^\x00-\x7F]+', '', text)
    # Remove phone numbers
    # phone = re.compile(r"(?:\+\d{1,3}\s?|\(\+\d{1,3}\)\s?|\d{0,4}\()?\d{1,4}\)?[-\s]?\d{1,4}[-\s]?\d{1,4}[-\s]?\d{0,4}")
    # text = re.sub(phone, '', text)
    # Remove social media references
    text = re.sub(r'Facebook|Twitter|LinkedIn|Instagram|Youtube|component.general.social-icon.icon-instagram', '', text)
    # Remove single characters followed by a colon
    text = re.sub(r'\b\w:\s*', '', text)
    # Remove "+" symbols
    text = re.sub(r'\+', '', text)
    # Remove redundant address data
    text = re.sub(r'(Suite|St)\s*\d+\s*', '', text)
    # Remove unnecessary punctuation and extra white spaces
    text = re.sub(r'[.,]', '', text)
    # text = re.sub(r'\s+', ' ', text).strip()
    return text




def get_soup(driver, url):
    """
    Fetch the HTML content from a URL and return a BeautifulSoup object.

    Args:
        driver (webdriver.Chrome): WebDriver instance to fetch the page source.
        url (str): URL of the webpage to be scraped.

    Returns:
        soup (BeautifulSoup): BeautifulSoup instance representing the HTML document.
    """
    driver.get(url)
    return BeautifulSoup(driver.page_source, "html.parser")


def decompose_elements(soup, elements):
    """
    Remove the specified elements from the BeautifulSoup object.

    Args:
        soup (BeautifulSoup): BeautifulSoup instance from which elements are to be removed.
        elements (list): List of HTML tags (as strings) to be removed.
    """
    for element in elements:
        for e in soup(element):
            e.decompose()


def get_clean_text(soup, blacklisted_strings):
    """
    Extract and return a clean version of the text from the BeautifulSoup object,
    excluding any blacklisted strings.

    Args:
        soup (BeautifulSoup): BeautifulSoup instance to extract text from.
        blacklisted_strings (list): List of strings to be excluded from the output.

    Returns:
        text (str): Cleaned text from the BeautifulSoup object.
    """
    return "\n".join(
        t.strip() for t in soup.stripped_strings if t not in blacklisted_strings
    )


def get_text_from_url(url):
    """
    Main function to fetch and clean the text from a given URL.

    Args:
        url (str): URL of the webpage to be scraped.

    Returns:
        text (str): Cleaned text from the webpage. Returns None if an error occurs during fetching or parsing.
    """
    try:
        # Initialize the WebDriver
        driver = setup_driver()

        # Get the BeautifulSoup object
        soup = get_soup(driver, url)

        # Elements to be removed from the page
        elements = [
            "script",
            "style",
            "head",
            "header",
            "footer",
            "aside",
            "img",
            "noscript",
            "nav",
            "form",
            "button",
            "fieldset",
            "input",
            "label",
        ]
        decompose_elements(soup, elements)

        # Strings to be removed from the page
        blacklisted_strings = [
            "Skip to content",
            "Your browser is out of date.",
            "The site might not be displayed correctly. Please update your browser.",
            "SEE ALL",

        ]
        driver.quit()
        text = get_clean_text(soup, blacklisted_strings)
        print('here text', text)
        text = clean_text(text)
        print('cleaned text', text)
        if len(text) > 6000:
            text = text[:6000]
        return text

    except Exception as e:
        print(f"Error occurred: {e}")
        return None


if __name__ == "__main__":
    url = "https://www.epam.com/about"
    analyzed_text = get_text_from_url(url)
    if analyzed_text:
        print('length of the text:', len(analyzed_text))
        print(analyzed_text)
    else:
        print("Could not fetch or parse text from the given URL")
