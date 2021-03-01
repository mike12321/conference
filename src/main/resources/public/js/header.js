init();

function init() {
    addEventListeners();
}

function addEventListeners() {
    for ( const cta of document.getElementsByClassName('localizeCta') ) {
        cta.addEventListener( 'click', handleLocaleChange);
    }
}

function handleLocaleChange( event ) {
    event.preventDefault();
    let redirectUrl = '';

    if ( window.location.search ) {
        const localeParamIndex = window.location.href.search( 'lang' );

        if ( localeParamIndex > 0 ) {
            const replaceString = `lang=${ event.target.dataset.locale }`;

            redirectUrl = `${ window.location.href.substring( 0, localeParamIndex ) }${ replaceString }${ window.location.href.substring( localeParamIndex + replaceString.length ) }`;
        }
        else {
            redirectUrl = `${ window.location.href }&lang=${ event.target.dataset.locale }`;
        }
    }
    else {
        redirectUrl = event.target.href;
    }

    window.location.href = redirectUrl;
}