const key = [35, 62, 248, 133, 97, 228, 157, 93, 234, 65, 156, 105, 206, 187, 183, 109, 121, 129, 31, 15, 32, 249, 146, 192, 197, 37, 0, 184, 60, 123, 15, 22]

let txtbox = document.getElementById("txtbox")
let decbox = document.getElementById("decbox")

function clear_decbox() {decbox.value = ""}
function clear_txtbox() {txtbox.value = ""}
function set_txtbox_placeholder(text) {
  txtbox.placeholder = text
}

function enc(text) {
  let textBytes = aesjs.utils.utf8.toBytes(text)
  let aesCtr = new aesjs.ModeOfOperation.ctr(key)
  let encryptedBytes = aesCtr.encrypt(textBytes)
  let encryptedHex = aesjs.utils.hex.fromBytes(encryptedBytes)
  return encryptedHex
}

function dec(text) {
  let encryptedHex = text
  let encryptedBytes = aesjs.utils.hex.toBytes(encryptedHex)
  let aesCtr = new aesjs.ModeOfOperation.ctr(key)
  let decryptedBytes = aesCtr.decrypt(encryptedBytes)
  let decryptedText = aesjs.utils.utf8.fromBytes(decryptedBytes)
  return decryptedText
}

function encrypt() {
  let text = txtbox.value
  let encText = enc(text)
  navigator.clipboard.writeText(encText)
  clear_txtbox()
  set_txtbox_placeholder("Awesome! Your message has been encrypted and copied. You can now send it to the other person.")
}

function decrypt() {
  let encText = decbox.value
  let otherText = dec(encText)
  let text = txtbox.value
  var query = new URLSearchParams({ "text1": text, "text2": otherText })
  fetch("https://twinword-text-similarity-v1.p.rapidapi.com/similarity/?" + query.toString(), {
    "method": "GET",
    "headers": {
      "x-rapidapi-host": "twinword-text-similarity-v1.p.rapidapi.com",
      "x-rapidapi-key": "04a4b6ce34msheac7d4925311190p1e673djsn63e92e05b2e3"
    }
  })
    .then(response => {
      response.json().then(json => {
        if (json["similarity"] > 0.5) {
          clear_decbox()
          clear_txtbox()
          set_txtbox_placeholder("YOU GOT IT\n\nThey said: " + text + "\n\nNow you can start again!\nEnter your message here and press encrypt")
        } else {
          clear_txtbox()
          set_txtbox_placeholder("Darn it! Not simmilar enough...\n\nYou can always try again!")
        }
      })
    })
    .catch(err => {
      console.log(err)
    })
}