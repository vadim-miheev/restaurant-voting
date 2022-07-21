'use strict';

// redirection page
document.addEventListener('DOMContentLoaded', () => {
  const redirectTo = 'https://restaurant-voting-application.herokuapp.com/';
  if (!window.InstallTrigger) {
    document.querySelector('#refresh').content = `0; URL='${redirectTo}'`;
  } else {
    window.setTimeout(() => {
      window.location.href = redirectTo;
    }, 6000);
  }
});
