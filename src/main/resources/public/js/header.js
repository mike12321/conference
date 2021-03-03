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
        const href = window.location.href;
        const localeParamIndex = href.search( 'lang' );
        const locale = event.target.dataset;

        if ( localeParamIndex > 0 ) {
            const replaceString = `lang=${ locale.locale }`;

            redirectUrl = `${ href.substring( 0, localeParamIndex ) }${ replaceString }${ href.substring( localeParamIndex + replaceString.length ) }`;
        }
        else {
            redirectUrl = `${ href }&lang=${ locale.locale }`;
        }
    }
    else {
        redirectUrl = event.target.href;
    }

    window.location.href = redirectUrl;
}