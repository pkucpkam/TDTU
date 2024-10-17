function loadImage() {
    let url = document.getElementById('imageUrl').value;
    let img = document.getElementById('image')
    let imgLink = document.getElementById('imageLink')

    let xhr = new XMLHttpRequest();
    xhr.addEventListener('load', e => {
        let { response } = xhr;
        let blob = URL.createObjectURL(response)
        image.src = blob
        imageLink.href = blob
    })

    xhr.open('GET', url, true);
    xhr.responseType = "blob";
    xhr.send();
}