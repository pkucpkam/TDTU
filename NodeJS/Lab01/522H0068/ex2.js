url = "students.json"
function loadStudent() {
    fetch(url)
        .then(response => {
            if (!response) {
                throw new Error("Error")
            }
            return response.json()
        })
        .then(data => {
            var tableBody = document.querySelector()
        })
        .catch (error => {
            console.error("Error")
        })
}